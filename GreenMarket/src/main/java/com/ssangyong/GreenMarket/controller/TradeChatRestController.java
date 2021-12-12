package com.ssangyong.GreenMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssangyong.GreenMarket.model.MessageEntity;
import com.ssangyong.GreenMarket.service.TradeChatService;

@RestController
@RequestMapping("tradeChat")
public class TradeChatRestController {
	@Autowired
	TradeChatService tradeChatService;
	
	@GetMapping("/getMessagesOfTrade/{tId}")
	public List<MessageEntity> getMessagesOfTrade(@PathVariable int tId) {
		List<MessageEntity> list = tradeChatService.getAllMessage(tId);
		return list;
	}
}
