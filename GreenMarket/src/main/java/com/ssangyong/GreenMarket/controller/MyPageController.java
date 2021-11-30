package com.ssangyong.GreenMarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssangyong.GreenMarket.service.MemberService;

public class MyPageController {
	
	 @Autowired
	   MemberService memberservice;
	   
		@PostMapping("/layout/account_setting")
		public void account_setting() {
			
			
		}
		

}
