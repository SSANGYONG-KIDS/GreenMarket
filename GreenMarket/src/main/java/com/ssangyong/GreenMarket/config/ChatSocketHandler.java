package com.ssangyong.GreenMarket.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ssangyong.GreenMarket.model.MessageEntity;
import com.ssangyong.GreenMarket.model.MsgEnumType;
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.TradeChatService;
import com.ssangyong.GreenMarket.service.TradeService;

// 채팅 소켓 핸들러
@Component("chatSocketHandler")
public class ChatSocketHandler extends TextWebSocketHandler{
	@Autowired
	TradeChatService tradeChatService;
	@Autowired
	LoginService loginService;
	@Autowired
	TradeService tradeService;
	
	// url
	static final String PATH = "chat";
	
	// 메시지 보낼 때 키
	static final String OBJ_KEY_TYPE = "type";
	static final String OBJ_KEY_CHAT_SESSION_ID = "chatSessionId";
	static final String OBJ_KEY_M_ID = "mId";
	static final String OBJ_KEY_CONTENT = "content";
	static final String OBJ_KEY_OTHER_M_ID = "otherMId"; // 상대방 m_id
	
	// 메시지 보낼 때 밸류
	static final String OBJ_TYPE_MESSAGE = "msg"; // 일반 텍스트 메시지
	static final String OBJ_TYPE_ALERT_OTHER_READ = "alertOtherRead"; // 상대방 읽음 알림 메시지 
	static final String OBJ_TYPE_AFTER_CHAT_CONNECTION = "afterChatConnection"; // 채팅 소켓 연결 직후 클라이언트가 보내는 메시지 
	static final String OBJ_TYPE_ALERT_ANOTHER_ROOM_MSG = "alertAnotherRoomMsg"; // 다른 방에 메시지 왔을 때 알리는 메시지
	
	// 맵
	Map<String, WebSocketSession> mapOfSession = new HashMap<>(); // 세션아이디, 세션
	Map<String, String> mapOfSessionId = new HashMap<>(); // 멤버아이디, 세션아이디
	Map<String, List<String>> mapOfMIdOfRoom = new HashMap<>(); // 방번호(tId; 거래 id), 멤버아이디

	/**
	 * 클라이언트로부터 메시지 받을 때 호출되는 메소드
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		// 서버에게 메시지를 보낸 세션
		String sessionId = session.getId();
		String tId = session.getUri().toString().split("/" + PATH +"/")[1]; // 거래번호
		String principalMId = session.getPrincipal().getName();
		
		// 서버가 받은 메시지
		JSONObject objReceived = tradeChatService.parseJsonToObject(message.getPayload());
		String type = (String) objReceived.get(OBJ_KEY_TYPE);
		
		// 받은 메시지의 종류가 일반 메시지일 경우
		if (type.equals(OBJ_TYPE_MESSAGE)) {
			
			// 서버가 받은 메시지
			String content = (String) objReceived.get(OBJ_KEY_CONTENT);
			String otherMId = (String) objReceived.get(OBJ_KEY_OTHER_M_ID);
			
			List<String> mIdsInRoom = mapOfMIdOfRoom.get(tId); // 같은 방에 있는 아이디(mId) 리스트
			int msgIsread = mIdsInRoom.size() > 1 ? 1 : 0; // 읽은여부
			
			// DB에 메시지 저장
			MessageEntity messageEntity = MessageEntity.builder()
								   					   .member(loginService.selectById(principalMId))
								   					   .msgContent(content)
								   					   .msgIsread(msgIsread)
								   					   .msgType(MsgEnumType.TEXT)
								   					   .trade(tradeService.selectById(Integer.parseInt(tId)))
								   					   .build();
			tradeChatService.insert(messageEntity);		
			
			// 같은 방 소켓 멤버들에게 메시지 보내기
			boolean isSentToOther = false; // 상대방에게 메시지 보냈는지 
			for (String mIdOfTarget : mIdsInRoom) {
				
				// 메시지 보낼 대상
				String sessionIdOfTarget = mapOfSessionId.get(mIdOfTarget);
				WebSocketSession sessionOfTarget = mapOfSession.get(sessionIdOfTarget);
				
				// 메시지 보낼 대상이 보낸 사람(자기 자신)일 때
				if (mIdOfTarget.equals(principalMId)) {
					
					// 상대방이 현재 소켓 연결되어 있다면 메시지 읽음처리하라고 알려주기
					if (msgIsread == 1) {

						// 보낼 메시지 객체
						JSONObject objForTarget = new JSONObject();
						objForTarget.put(OBJ_KEY_TYPE, OBJ_TYPE_ALERT_OTHER_READ); // 메시지 종류
						
						// 메시지 보내기
						sessionOfTarget.sendMessage(new TextMessage(objForTarget.toJSONString()));
					}
					
					// 이외의 경우는 메시지 보내지 않고 그냥 넘기기
					else {
						continue;
					}
				}
				
				// 메시지 보낼 대상이 상대방일 때
				else if (!mIdOfTarget.equals(principalMId)) {
					
					// 보낼 메시지 객체
					JSONObject objForTarget = new JSONObject();
					objForTarget.put(OBJ_KEY_TYPE, OBJ_TYPE_MESSAGE);
					objForTarget.put(OBJ_KEY_M_ID, principalMId);
					objForTarget.put(OBJ_KEY_CONTENT, content);
					
					// 메시지 보내기
					sessionOfTarget.sendMessage(new TextMessage(objForTarget.toJSONString()));
					isSentToOther = true; // 상대방에게 메시지 보낸 여부 true로
				}
			}
			// -- 같은 방 소켓 멤버들에게 메시지 보내기 끝 --/
			
			// 상대방에게 메시지 보내지 않았을 때
			if (!isSentToOther) {
				// 상대방이 다른 소켓에 연결되어 있다면
				if (mapOfSessionId.containsKey(otherMId)) {

					// 메시지 보낼 대상
					String sessionIdOfTarget = mapOfSessionId.get(otherMId);
					WebSocketSession sessionOfTarget = mapOfSession.get(sessionIdOfTarget);
					
					// 보낼 메시지 객체
					JSONObject objForTarget = new JSONObject();
					objForTarget.put(OBJ_KEY_TYPE, OBJ_TYPE_ALERT_ANOTHER_ROOM_MSG); // 메시지 종류
					objForTarget.put(OBJ_KEY_CONTENT, tId);
					
					// 메시지 보내기 (다른방에서 메시지 왔음 알리기)
					sessionOfTarget.sendMessage(new TextMessage(objForTarget.toJSONString()));
				}
			}
		}
	}	
	
	/**
	 *  소켓 연결이 되었을 때 호출되는 메소드
	 */
	@SuppressWarnings("unchecked")	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		// 세션 연결
		super.afterConnectionEstablished(session);
		String sessionId = session.getId();
		String tId = session.getUri().toString().split("/" + PATH +"/")[1]; // 거래번호
		String principalMId = session.getPrincipal().getName();
		
		/**
		 * 맵에 세션 저장
		 */	
		
		// 세션 맵에 저장
		mapOfSession.put(sessionId, session);
		
		// 세션아이디 맵에 저장
		mapOfSessionId.put(principalMId, session.getId());
		
		// 방 목록 맵에 저장
		if (!mapOfMIdOfRoom.containsKey(tId)) { // 현재 해당 방에 연결된 소켓이 하나도 없을 때
			mapOfMIdOfRoom.put(tId, new ArrayList<String>());
		}
		List<String> mIdsInRoom = mapOfMIdOfRoom.get(tId);
		mIdsInRoom.add(principalMId);
	
		
		/**
		 * 클라이언트에게 메시지 전송
		 * 전송 항목: 세션아이디
		 */
		JSONObject obj = new JSONObject();
		obj.put(OBJ_KEY_TYPE, OBJ_TYPE_AFTER_CHAT_CONNECTION);
		obj.put(OBJ_KEY_CHAT_SESSION_ID, session.getId());
		session.sendMessage(new TextMessage(obj.toJSONString()));
		
		// 소켓이 로비 연결이 아닐 때
		if (Integer.parseInt(tId) != 0) {
			
			// DB에 상대방 메시지 읽음 처리 하기
			tradeChatService.changeToReadState(Integer.parseInt(tId), principalMId);
			
			/**
			 * 상대방에게 메시지 읽음 처리하라고 알려주기
			 */
			// 같은 방 소켓 멤버들에게 메시지 보내기		
			for (String mIdOfTarget : mIdsInRoom) {
				
				// 메시지 보낼 대상
				String sessionIdOfTarget = mapOfSessionId.get(mIdOfTarget);
				WebSocketSession sessionOfTarget = mapOfSession.get(sessionIdOfTarget);
				
				// 메시지 보낼 대상이 상대방일 때
				if (!mIdOfTarget.equals(principalMId)) {
					
					// 보낼 메시지 객체
					JSONObject objForTarget = new JSONObject();
					objForTarget.put(OBJ_KEY_TYPE, OBJ_TYPE_ALERT_OTHER_READ); // 메시지 종류
					
					// 메시지 보내기
					sessionOfTarget.sendMessage(new TextMessage(objForTarget.toJSONString()));
				}
			}						
		}
	}
	
	
	/**
	 * 소켓 연결 종료했을 때 호출되는 메소드
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		// 종료되는 세션
		String sessionId = session.getId();
		String tId = session.getUri().toString().split("/" + PATH +"/")[1]; // 거래번호
		String principalMId = session.getPrincipal().getName();
		
		// 세션 맵, 세션아이디 맵, 방 목록 맵에서 제거
		mapOfSession.remove(sessionId);
		mapOfSessionId.remove(principalMId);
		List<String> mIdsInRoom = mapOfMIdOfRoom.get(tId);
		mIdsInRoom.remove(principalMId);
		
		// 마지막으로 super 호출
		super.afterConnectionClosed(session, status);
	}
	
	
}
