package com.ssangyong.GreenMarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssangyong.GreenMarket.model.MemberAddress;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.service.LoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //?
@Controller
@RequestMapping 
public class LoginController {
	
	@Autowired
	LoginService loginservice;
	
	
	@GetMapping("/layout/login")
	public void login() {
	}
	
	
	@PostMapping( value = "/layout/signup")
	public String signup(MemberEntity member, String memberAddress1, String memberAddress2, String memberAddress3, String AddNum ) { // 회원 추가
	   MemberAddress memberAddress = new MemberAddress();
	   memberAddress.setAddNum(AddNum);
	   memberAddress.setMemberAddress1(memberAddress1);
	   memberAddress.setMemberAddress2(memberAddress2);
	   memberAddress.setMemberAddress3(memberAddress3);
	     
	   loginservice.signup(member, memberAddress); 
	   return "redirect:/index";
	 }
	
	 @GetMapping("/layout/signup")
	 public void signupget() { // 회원 추가
		
	 }
	 
	 
	
}