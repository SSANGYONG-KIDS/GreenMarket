package com.ssangyong.GreenMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssangyong.GreenMarket.model.MessageEntity;
import com.ssangyong.GreenMarket.model.SecurityUser;
import com.ssangyong.GreenMarket.model.TradeEntity;
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.TradeChatService;
import com.ssangyong.GreenMarket.service.TradeService;

@RestController
@RequestMapping("tradeChat")
public class TradeChatRestController {
	@Autowired
	LoginService loginService;		
	@Autowired
	TradeService tradeService;	
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
	 * 모든 안읽은 메시지 수 보내주기
	 */
	@GetMapping("/getAllCntOfUnread")
	public int getAllCntOfUnread(@AuthenticationPrincipal SecurityUser principal) {
      	int allCntOfUnread = 0; // 안읽은 메시지 수
      	if (principal != null) {
    		// 내 아이템에 대한 거래 목록 가져오기
    		List<TradeEntity> tradesForSharer = tradeService.listTradeForSharer(loginService.selectById(principal.getUsername()));
    		// 내가 예약한 거래 목록 가져오기
    		List<TradeEntity> tradesForRenter = tradeService.listTradeForRenter(loginService.selectById(principal.getUsername()));
    		
    		// 안읽은 메시지 수 가져오기
			for (TradeEntity trade : tradesForSharer) {
				int cntOfUnread = tradeChatService.getCntOfUnreadMsg(trade.getTId(), principal.getUsername());
				allCntOfUnread += cntOfUnread;
			}
			for (TradeEntity trade : tradesForRenter) {
				int cntOfUnread = tradeChatService.getCntOfUnreadMsg(trade.getTId(), principal.getUsername());
				allCntOfUnread += cntOfUnread;
			}    		
      	}
      	
      	return allCntOfUnread;
	}
	

// 테스트	
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
