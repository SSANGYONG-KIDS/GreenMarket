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
	
	/**
	 * 거래 메시지 가져오기
	 * @param tId
	 * @return
	 */
	@GetMapping("/getMessagesOfTrade/{tId}")
	public List<MessageEntity> getMessagesOfTrade(@PathVariable int tId) {
		List<MessageEntity> list = tradeChatService.getAllMessage(tId);
		return list;
	}
	
	
	/**
	 * 메시지 읽음 처리 테스트
	 * @param tId: 거래 번호
	 * @param mId: 읽은 사람 아이디
	 */
	@GetMapping("/test/changeToReadState/{tId}/{mId:.+}")
	public void testChangeToReadState(@PathVariable int tId, @PathVariable String mId) {
		tradeChatService.changeToReadState(tId, mId);
	}
}
