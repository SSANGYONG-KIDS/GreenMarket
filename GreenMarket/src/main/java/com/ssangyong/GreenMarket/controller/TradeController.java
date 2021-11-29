package com.ssangyong.GreenMarket.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssangyong.GreenMarket.model.ItemPageVO;
import com.ssangyong.GreenMarket.model.SecurityUser;

@Controller
@RequestMapping("trade")
public class TradeController {
	
	@RequestMapping("tempShowItemList")
	public void tempShowItemList() {

	}
	
	/**
	 * 예약 작성 페이지
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
	 * 예약 페이지
	 */
	@PostMapping("reserve")
	@ResponseBody
	public String reserve() {
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
