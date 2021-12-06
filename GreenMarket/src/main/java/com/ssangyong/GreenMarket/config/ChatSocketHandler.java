package com.ssangyong.GreenMarket.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ssangyong.GreenMarket.service.TradeChatService;

// 채팅 소켓 핸들러
@Component("chatSocketHandler")
public class ChatSocketHandler extends TextWebSocketHandler{
	@Autowired
	TradeChatService tradeChatService;
	
	static final String PATH = "chat";
	static final String TYPE_MESSAGE = "msg";
	Map<String, List<WebSocketSession>> mapOfRoom = new HashMap<>(); // 방번호, 세션리스트

	/**
	 * 클라이언트로부터 메시지 받을 때 호출되는 메소드
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("-- handleTextMessage in ChatSocketHandler");
		JSONObject objReceived = tradeChatService.parseJsonToObject(message.getPayload());
		String type = (String) objReceived.get("type");
		
		// 받은 메시지의 종류가 일반 메시지일 경우
		if (type.equals(TYPE_MESSAGE)) {
			String msg = (String) objReceived.get("msg");
			System.out.println("mIdOfsender: " + session.getPrincipal().getName());
			System.out.println("msg: " + msg);			
		}
	}	
	
	/**
	 *  소켓 연결이 되었을 때 호출되는 메소드
	 */
	@SuppressWarnings("unchecked")	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("-- afterConnectionEstablished in ChatSocketHandler");
		System.out.println("session.getId(): " + session.getId());
		
		// 세션 연결
		super.afterConnectionEstablished(session);
		String tId = session.getUri().toString().split("/" + PATH +"/")[1]; // 거래번호
		System.out.println("tId: " + tId);
		System.out.println("session.getPrincipal().getName(): " + session.getPrincipal().getName()); // 세션 m_id
		
		/**
		 * 맵에 세션 저장
		 */
		// 해당 방 번호가 맵에 없다면 put
		if (!mapOfRoom.containsKey(tId)) {
			mapOfRoom.put(tId, new ArrayList<WebSocketSession>());
		}
		
		// 리스트에 세션 저장
		List<WebSocketSession> sessionsInRoom = mapOfRoom.get(tId);
		sessionsInRoom.add(session);
		
		/**
		 * 클라이언트에게 메시지 전송
		 * 전송 항목: 세션아이디
		 */
		JSONObject obj = new JSONObject();
		obj.put("type", "afterChatConnection");
		obj.put("chatSessionId", session.getId());
		session.sendMessage(new TextMessage(obj.toJSONString()));
	}


}
