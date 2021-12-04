package com.ssangyong.GreenMarket.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component("chatSocketHandler")
public class ChatSocketHandler extends TextWebSocketHandler{
	
}
