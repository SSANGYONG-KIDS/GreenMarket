package com.ssangyong.GreenMarket.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.ReviewEntity;
import com.ssangyong.GreenMarket.model.TradeEntity;
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.ReviewService;
import com.ssangyong.GreenMarket.service.TradeService;

@Controller
@RequestMapping("/review/*")
public class ReviewController {
	@Autowired
	ReviewService reviewService;
	@Autowired
	LoginService loginService;
	@Autowired
	TradeService tradeService;
	
	
	@GetMapping("/myreviewlist")
	public void selectMyReviewList(Model model, HttpServletRequest request, Principal principal, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		List<ReviewEntity> myreviewlist = (List<ReviewEntity>)reviewService.selectMyList(member);
		List<TradeEntity> myTradelist = tradeService.listTradeForRenter(member);
		
		model.addAttribute("myreviewlist", myreviewlist);
		model.addAttribute("myTradelist", myTradelist);
	}
	
	@GetMapping("/review/delete")
	public String deleteMyReview(Model model, Integer rId, Principal principal, Authentication authentication) {
		int result = reviewService.deleteReview(rId);
		
		model.addAttribute("result", result);
		return "redirect:/review/myreviewlist";
	}
}
