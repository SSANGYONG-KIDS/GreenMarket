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
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.ReviewService;

@Controller
@RequestMapping("/review/*")
public class ReviewController {
	@Autowired
	ReviewService reviewService;
	@Autowired
	LoginService loginService;
	
	@GetMapping("/myreviewlist")
	public void selectMyReviewList(Model model, HttpServletRequest request, Principal principal, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberEntity member =  loginService.selectById(userDetails.getUsername());
		model.addAttribute("member",member);
		
		List<ReviewEntity> myreviewlist = (List<ReviewEntity>)reviewService.selectMyList(member);

		model.addAttribute("myreviewlist", myreviewlist);
	}
}
