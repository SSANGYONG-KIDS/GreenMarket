package com.ssangyong.GreenMarket.controller;

import java.util.Random;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssangyong.GreenMarket.model.MemberAddress;
import com.ssangyong.GreenMarket.model.MemberEntity;
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //?
@Controller
@RequestMapping 
public class LoginController {
   
   @Autowired
   LoginService loginservice;
   
   @Autowired
   MemberService memberservice;
   

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

//   public MemberEntity getUser() { //
//	   MemberEntity member = new MemberEntity();
//       SecurityContext ctx = SecurityContextHolder.getContext();
//       Authentication auth = ctx.getAuthentication();
//       if (auth.getPrincipal() instanceof UserDetails) user = (UserVO) auth.getPrincipal();
//       return member;
//   }
//
//   public HttpServletRequest getRequest() {
//       return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//   }
//   
//   
   @RequestMapping("/login/testtest")
   @ResponseBody
   public String SMSController(String mPhone) {
       Random rand  = new Random();
       String numStr = "";
       for(int i=0; i<4; i++) {
           String ran = Integer.toString(rand.nextInt(10));
           numStr+=ran;
       }
       loginservice.certifiedPhoneNumber(mPhone,numStr);
       return numStr;
   }
   
   @ResponseBody
   @GetMapping("/login/nickCheck")
   public int CheckNickName(String mNickname) {
      return loginservice.checkNickName(mNickname)? 0: 1;
   }
   
   @ResponseBody
   @GetMapping("/login/duCheck")
   public int checkId(String mId){
      return loginservice.checkName(mId)? 0 : 1;
   }
   
   
}