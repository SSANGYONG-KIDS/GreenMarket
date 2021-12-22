package com.ssangyong.GreenMarket.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
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

import com.ssangyong.GreenMarket.model.ItStateEnumType;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPageVO;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.SecurityUser;
import com.ssangyong.GreenMarket.model.TStateEnumType;
import com.ssangyong.GreenMarket.model.TradeEntity;
import com.ssangyong.GreenMarket.service.ItemService;
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.TradeChatService;
import com.ssangyong.GreenMarket.service.TradeService;

@Controller
@RequestMapping("trade")
public class TradeController {
	
	@Autowired
	TradeService tradeService;
	@Autowired
	TradeChatService tradeChatService;
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
	
		// 입력받은 예약 날짜가 존재하지 않을 때
		if (itemPageVO.getStartDate() == null || itemPageVO.getStartDate().equals("")) { // 시작일에 대해서
			itemPageVO.setStartDate(LocalDate.now().toString()); // 현재 날짜 가져오기
		}
		if (itemPageVO.getEndDate() == null || itemPageVO.getEndDate().equals("")) { // 종료일에 대해서
			itemPageVO.setEndDate(itemPageVO.getStartDate()); // 시작일 가져오기
		}
			
		// 날짜 한글 형식으로 바꾸기
		String startDateKor = tradeService.convertFormToKorDate(itemPageVO.getStartDate());
		String endDateKor = tradeService.convertFormToKorDate(itemPageVO.getEndDate());
		System.out.println("startDateKor: " + startDateKor + ", endDateKor: " + endDateKor);
		
		// item 가져오기
		ItemEntity item = itemService.selectById(iId);
		
		
		// model에 값 넣기
		model.addAttribute("iId", iId);
		model.addAttribute("itemPageVO", itemPageVO);
		model.addAttribute("startDateKor", startDateKor);
		model.addAttribute("endDateKor", endDateKor);
		model.addAttribute("startDate", itemPageVO.getStartDate());
		model.addAttribute("endDate", itemPageVO.getEndDate());
		model.addAttribute("item", item);
		
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
		
		// Timestamp 형식으로 변경
		startDate += " 00:00:00";
		endDate += " 00:00:00";
		
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
	 * 내 거래 목록 보기
	 */
	@RequestMapping("myTrade")
	public void myTrade(Model model, @AuthenticationPrincipal SecurityUser principal) {
		// 세션 멤버 정보 가져오기
		MemberEntity MemberOfPrincipal = loginService.selectById(principal.getUsername());
		Map<Object, Object> mapOfPrincipal = new HashMap<>(); // 필요한 정보만 맵에 담기
		mapOfPrincipal.put("mId", MemberOfPrincipal.getMId());
		mapOfPrincipal.put("mNickname", MemberOfPrincipal.getMNickname());
		mapOfPrincipal.put("mPhoto", MemberOfPrincipal.getMPhoto());
		model.addAttribute("mapOfPrincipal", mapOfPrincipal);
		
		// 내 아이템에 대한 거래 목록 가져오기
		List<TradeEntity> tradesForSharer = tradeService.listTradeForSharer(loginService.selectById(principal.getUsername()));
		model.addAttribute("tradesForSharer", tradesForSharer);
		
		// 내가 예약한 거래 목록 가져오기
		List<TradeEntity> tradesForRenter = tradeService.listTradeForRenter(loginService.selectById(principal.getUsername()));
		model.addAttribute("tradesForRenter", tradesForRenter);
		
		// 아이템 거래상태 정보 가져오기
		model.addAttribute("tStates", TStateEnumType.values());
	}
	
	/**
	 * 거래상태 변경하기
	 */
	@PostMapping("changeTStates")
	@ResponseBody
	public String changeTStates(int tId, String tStateValue) {
		// tState 변경하기
		tradeService.changeTState(tId, tStateValue);
		return "";
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

		// 세션 멤버 정보 가져오기
		MemberEntity MemberOfPrincipal = loginService.selectById(principal.getUsername());
		Map<Object, Object> mapOfPrincipal = new HashMap<>(); // 필요한 정보만 맵에 담기
		mapOfPrincipal.put("mId", MemberOfPrincipal.getMId());
		mapOfPrincipal.put("mNickname", MemberOfPrincipal.getMNickname());
		mapOfPrincipal.put("mPhoto", MemberOfPrincipal.getMPhoto());
		model.addAttribute("mapOfPrincipal", mapOfPrincipal);
		
		// 내 아이템에 대한 거래 목록 가져오기
		List<TradeEntity> tradesForSharer = tradeService.listTradeForSharer(loginService.selectById(principal.getUsername()));
		model.addAttribute("tradesForSharer", tradesForSharer);
		
		// 내가 예약한 거래 목록 가져오기
		List<TradeEntity> tradesForRenter = tradeService.listTradeForRenter(loginService.selectById(principal.getUsername()));
		model.addAttribute("tradesForRenter", tradesForRenter);
		
		// 안읽은 메시지 수 가져오기
		List<Integer> cntsUnreadForSharer = new ArrayList<>();
		for (TradeEntity trade : tradesForSharer) {
			int cntOfUnread = tradeChatService.getCntOfUnreadMsg(trade.getTId(), principal.getUsername());
			cntsUnreadForSharer.add(cntOfUnread);
		}
		model.addAttribute("cntsUnreadForSharer", cntsUnreadForSharer);
		
		List<Integer> cntsUnreadForRenter = new ArrayList<>();
		for (TradeEntity trade : tradesForRenter) {
			int cntOfUnread = tradeChatService.getCntOfUnreadMsg(trade.getTId(), principal.getUsername());
			cntsUnreadForRenter.add(cntOfUnread);
		}		
		model.addAttribute("cntsUnreadForRenter", cntsUnreadForRenter);
	}
}
