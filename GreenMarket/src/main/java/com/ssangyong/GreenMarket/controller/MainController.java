package com.ssangyong.GreenMarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssangyong.GreenMarket.model.ICategoryEnumType;
import com.ssangyong.GreenMarket.model.SecurityUser;
import com.ssangyong.GreenMarket.service.MemberService;

@Controller
public class MainController {
	
	   @Autowired
	   MemberService memberService;
	   
	   @RequestMapping(value = {"/", "/index"})
	   public String main(Model model, @AuthenticationPrincipal SecurityUser principal) {
	      System.out.println("main");
	      
	      // 탈퇴한 회원인 경우
	      if (principal != null && memberService.selectById(principal.getUsername()).getMIsdropped() == 1) {
	    	  model.addAttribute("isDropped", 1);
	      } else {
	    	  model.addAttribute("isDropped", 0);
	      }
	      
	      model.addAttribute("itemSorts", ICategoryEnumType.values());
	      return "index";
	   }
	   
	   @RequestMapping("/layout/about")
	   public void about() {}
	   
	   @RequestMapping("/layout/destination")
	   public void destination() {}
	   
	   @RequestMapping("/layout/hotel")
	   public void hotel() {}
	   
	   @RequestMapping("/layout/blog")
	   public void blog() {}
	   
	   @RequestMapping("/layout/myPage")
	   public void mypage() {}
	   
	   @RequestMapping("/layout/signup")
	   public void signup() {}
}
