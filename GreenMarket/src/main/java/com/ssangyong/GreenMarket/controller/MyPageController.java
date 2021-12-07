package com.ssangyong.GreenMarket.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.model.SecurityUser;
import com.ssangyong.GreenMarket.service.MemberService;

@Controller
public class MyPageController {
	
	 @Autowired
	   MemberService memberservice;
	   
	 @GetMapping("/layout/account_setting")
	   public String selectById(Model model, @AuthenticationPrincipal SecurityUser principal) {
	      System.out.println("회원정보 보기 controller");
	      System.out.println(principal);
	      model.addAttribute("member", memberservice.selectById(principal.getUsername()));
	      return "/layout/account_setting";
 }
	 
	 @PostMapping("/modify")
	 @ResponseBody
		public void myModify(MemberEntity member, @AuthenticationPrincipal SecurityUser principal) {
		 System.out.println("회원정보 수정하기");
		 member.setMId(principal.getUsername());
		 memberservice.updateMember(member);
	 }
	 
	 @PostMapping("/delete")
	 @ResponseBody
		public void delete(String mId) {
		 System.out.println("회원 탈퇴");
		 memberservice.deleteById(mId);
	 }
}
