package com.ssangyong.GreenMarket.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPageVO;
import com.ssangyong.GreenMarket.model.SecurityUser;
import com.ssangyong.GreenMarket.model.TStateEnumType;
import com.ssangyong.GreenMarket.model.TradeEntity;
import com.ssangyong.GreenMarket.service.ItemService;
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.TradeService;

@Controller
@RequestMapping("trade")
public class TradeController {
	
	@Autowired
	TradeService tradeService;
	@Autowired
	ItemService itemService;
	@Autowired
	LoginService loginService;
	
	@RequestMapping("tempShowItemList")
	public void tempShowItemList() {

	}
	
	/**
	 * 예약 작성 페이지
	 * df
	 */
	@RequestMapping("reserveForm/{iId:.+}")
	public String reserveForm(@PathVariable int iId, @AuthenticationPrincipal SecurityUser principal, Model model, ItemPageVO itemPageVO) {
		System.out.println("reserveForm in TradeController");
		System.out.println("iId: " + iId);
		System.out.println("principal: " + principal);
		
		model.addAttribute("iId", iId);
		model.addAttribute("itemPageVO", itemPageVO);
		
		return "trade/reserveForm";
	}
	
	/**
	 * 예약하기. TRADE 테이블에 거래 데이터 삽입.
	 * AJAX, POST
	 * @param iId
	 * @param startDate
	 * @param endDate
	 * @param principal
	 * @param model
	 * @return
	 */
	@PostMapping("reserve")
	@ResponseBody
	public String reserve(int iId, String startDate, String endDate, @AuthenticationPrincipal SecurityUser principal, Model model) {
		System.out.println("controller: trade/reserve");
		
		// Timestamp 형식으로 변경
		startDate += " 00:00:00";
		endDate += " 00:00:00";
		
		// TradeEntity에 넣기 전 콘솔 확인
		System.out.println("sessionId: " + principal.getUsername() + ", iId: " + iId + ", startDate: " + startDate + ", endDate: " + endDate);
		
		// TradeEntity에 정보 넣기
		TradeEntity trade = TradeEntity.builder()
								.item(itemService.selectById(iId))
								.tStartdate(Timestamp.valueOf(startDate))
								.tEnddate(Timestamp.valueOf(endDate))
								.tState(TStateEnumType.WAIT)
								.buyMember(loginService.selectById(principal.getUsername()))
								.build();

		// Trade 테이블에 거래 데이터 추가하기
		tradeService.insertTrade(trade);
		return "success";
	}
	
	
	/**
	 * 세션 테스트
	 */
	@RequestMapping("sessionTest")
	@ResponseBody
	public String sessionTest(@AuthenticationPrincipal SecurityUser principal) {
		System.out.println(principal);
		return null;
	}
	
	/**
	 * s3테스트
	 */
	@RequestMapping("s3test")
	public void s3Test() {
		
	}
}
