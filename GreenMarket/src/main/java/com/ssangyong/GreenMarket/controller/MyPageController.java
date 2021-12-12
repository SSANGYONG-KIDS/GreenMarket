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

import com.ssangyong.GreenMarket.model.MemberAddress;
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
		public void myModify(MemberEntity member,String memberAddress1, String memberAddress2, String memberAddress3, String AddNum, @AuthenticationPrincipal SecurityUser principal) {
		 System.out.println("회원정보 수정하기");
		 member.setMId(principal.getUsername());
		 MemberAddress memberAddress = new MemberAddress();
	      memberAddress.setAddNum(AddNum);
	      memberAddress.setMemberAddress1(memberAddress1);
	      memberAddress.setMemberAddress2(memberAddress2);
	      memberAddress.setMemberAddress3(memberAddress3);
	      memberservice.updateMember(member);
	 }
	 
	 @PostMapping("/delete")
	 @ResponseBody
		public void delete(@AuthenticationPrincipal SecurityUser principal) {
		 System.out.println("회원 탈퇴");
		 memberservice.deleteById(principal.getUsername());
	 }
}
