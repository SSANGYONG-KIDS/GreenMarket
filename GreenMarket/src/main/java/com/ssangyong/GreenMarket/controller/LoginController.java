package com.ssangyong.GreenMarket.controller;

import java.security.Principal;
import java.util.Random;

import javax.servlet.http.HttpSession;

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
	
	
	@GetMapping("/login")
	public void login() {
		
	}
	@GetMapping("/signup")
	public void signup() {
		
	}
	
	 @PostMapping( value = "/login/signUp")
	    public String signup(MemberEntity member, String userAddress1, String userAddress2, String userAddress3, String AddNum ) { // 회원 추가
	       MemberAddress memberAddress = new MemberAddress();
	       memberAddress.setAddNum(AddNum);
//	       memberAddress.setUserAddress1(userAddress1);
//	       memberAddress.setUserAddress2(userAddress2);
//	       memberAddress.setUserAddress3(userAddress3);
	      
	  
	      return "redirect:/index";
	    }
	
	
}