package com.ssangyong.GreenMarket.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ssangyong.GreenMarket.model.MemberEntity;
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
	
	/**
	 * 아이템 가져오기 테스트
	 */
	@RequestMapping("tempShowItemList")
	public void tempShowItemList() {

	}
	
	/**
	 * 예약 작성 페이지
	 * 
	 */
	@RequestMapping("reserveForm/{iId:.+}")
	public String reserveForm(@PathVariable int iId, @AuthenticationPrincipal SecurityUser principal, Model model, ItemPageVO itemPageVO) {
		System.out.println("-- reserveForm in TradeController");
		System.out.println("iId: " + iId);
		System.out.println("principal: " + principal);
		System.out.println("itemPageVO: " + itemPageVO);
		
		// 날짜 한글 형식으로 바꾸기
		if (itemPageVO.getStartDate() == null) itemPageVO.setStartDate("---");
		if (itemPageVO.getEndDate() == null) itemPageVO.setEndDate("---");
		String startDateKor = tradeService.convertFormToKorDate(itemPageVO.getStartDate());
		String endDateKor = tradeService.convertFormToKorDate(itemPageVO.getEndDate());
		System.out.println("startDateKor: " + startDateKor + ", endDateKor: " + endDateKor);
		
		// model에 값 넣기
		model.addAttribute("iId", iId);
		model.addAttribute("itemPageVO", itemPageVO);
		model.addAttribute("startDateKor", startDateKor);
		model.addAttribute("endDateKor", endDateKor);
		
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
		System.out.println("-- reserve in TradeController");
		
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
	
	/**
	 * 채팅 메인 화면
	 */
	@RequestMapping("chatMain")
	public void chatMain(Model model, @AuthenticationPrincipal SecurityUser principal) {
		System.out.println("controller: trade/chatMain");
		
		// 세션 멤버 정보 가져오기
		MemberEntity MemberOfPrincipal = loginService.selectById(principal.getUsername());
		Map<Object, Object> mapOfPrincipal = new HashMap<>(); // 필요한 정보만 맵에 담기
		mapOfPrincipal.put("mId", MemberOfPrincipal.getMId());
		mapOfPrincipal.put("mNickname", MemberOfPrincipal.getMNickname());
		model.addAttribute("mapOfPrincipal", mapOfPrincipal);
		
		// 내 아이템에 대한 거래 목록 가져오기
		List<TradeEntity> tradesForSharer = tradeService.listTradeForSharer(loginService.selectById(principal.getUsername()));
		model.addAttribute("tradesForSharer", tradesForSharer);
		
		// 내가 예약한 거래 목록 가져오기 (TODO)
		List<TradeEntity> tradesForRenter = tradeService.listTradeForRenter(loginService.selectById(principal.getUsername()));
		model.addAttribute("tradesForRenter", tradesForRenter);
	}
	
	/**
	 * 테스트
	 */
	@RequestMapping("test")
	@ResponseBody
	public String test() {
		// 내가 구매한 거래 내역 가져오기
		return tradeService.listTradeForRenter(loginService.selectById("test1")).toString();
		
		// 내가 판매한 거래 내역 가져오기
//		List<TradeEntity> list = tradeService.listTradeForSharer(loginService.selectById("test1"));
//		System.out.println("before sysout");
//		for (TradeEntity val : list) {
//			System.out.println(val.getTId());
//			System.out.println(val.getItem().getMember().getMId());
//		}
//		return null; 
	}

}
