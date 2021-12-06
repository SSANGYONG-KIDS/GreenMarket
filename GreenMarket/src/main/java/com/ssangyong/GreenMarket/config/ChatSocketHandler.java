package com.ssangyong.GreenMarket.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// 채팅 소켓 핸들러
@Component("chatSocketHandler")
public class ChatSocketHandler extends TextWebSocketHandler{
	static final String PATH = "chat";
	Map<String, List<WebSocketSession>> mapOfRoom = new HashMap<>(); // 방번호, 세션리스트

	// 소켓 연결이 되었을 때
	@SuppressWarnings("unchecked")	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("-- afterConnectionEstablished in ChatSocketHandler");
		System.out.println("session.getId(): " + session.getId());
		
		// 세션 연결
		super.afterConnectionEstablished(session);
		String roomNumber = session.getUri().toString().split("/" + PATH +"/")[1]; // 방번호
		System.out.println("roomNumber: " + roomNumber);
		System.out.println("session.getPrincipal().getName(): " + session.getPrincipal().getName()); // 세션 m_id
		
		/**
		 * 맵에 세션 저장
		 */
		// 해당 방 번호가 맵에 없다면 put
		if (!mapOfRoom.containsKey(roomNumber)) {
			mapOfRoom.put(roomNumber, new ArrayList<WebSocketSession>());
		}
		
		// 리스트에 세션 저장
		List<WebSocketSession> sessionsInRoom = mapOfRoom.get(roomNumber);
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
