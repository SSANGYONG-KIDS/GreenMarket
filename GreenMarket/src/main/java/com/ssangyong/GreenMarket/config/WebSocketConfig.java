package com.ssangyong.GreenMarket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;


// 소켓 설정
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	static final int BUFFER_SIZE = 3000000; // 메시지 버퍼 크기
	
	// 채팅 소켓
	@Autowired
	@Qualifier("chatSocketHandler")
	ChatSocketHandler chatSocketHandler;
	
	// 핸들러 등록
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatSocketHandler, "/chat/{roomNumber}");
	}

	// 메시지 버퍼 크기 설정
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(BUFFER_SIZE);
		container.setMaxBinaryMessageBufferSize(BUFFER_SIZE);
		return container;
	}
}
