package com.ssangyong.GreenMarket.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssangyong.GreenMarket.model.CommunityEntity;
import com.ssangyong.GreenMarket.model.ICategoryEnumType;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPageVO;
import com.ssangyong.GreenMarket.model.PageMaker;
import com.ssangyong.GreenMarket.model.PageVO;
import com.ssangyong.GreenMarket.model.SecurityUser;
import com.ssangyong.GreenMarket.service.CommunityService;
import com.ssangyong.GreenMarket.service.ItemPhotoService;
import com.ssangyong.GreenMarket.service.ItemService;
import com.ssangyong.GreenMarket.service.LoginService;
import com.ssangyong.GreenMarket.service.MemberService;

import edu.emory.mathcs.backport.java.util.Collections;

@Controller
public class MainController {
	
		@Autowired
		MemberService memberService;
		@Autowired
		ItemService itemService;
		@Autowired
		ItemPhotoService itemPhotoService;
		@Autowired
		LoginService loginService;
		@Autowired
		CommunityService communityService;
	   
	   @RequestMapping(value = {"/", "/index"})
	   public String main(Model model, @AuthenticationPrincipal SecurityUser principal, boolean showsLoginForm) {
	      System.out.println("main 실행");
	      
	      // 로그인폼을 보여줄 것인지에 대한 변수
	      model.addAttribute("showsLoginForm", showsLoginForm);
	      
	      // 탈퇴한 회원인 경우
	      if (principal != null && memberService.selectById(principal.getUsername()).getMIsdropped() == 1) {
	    	  model.addAttribute("isDropped", 1);
	      } else {
	    	  model.addAttribute("isDropped", 0);
	      }

		  	// 아이템 목록
	      	ItemPageVO pagevo = new ItemPageVO(1, 6, null, null, null, null, 0);
			Page<ItemEntity> result = itemService.selectAll(pagevo);
			
			for(ItemEntity item : result) {
				if(item.getIContent().length()>=10) {
					String sub = item.getIContent().substring(0, 10)+" ...";
					item.setIContent(sub);
				}
				System.out.println(item);
			}	      
		    model.addAttribute("itemSorts", ICategoryEnumType.values());
			model.addAttribute("itemResult", result);
			model.addAttribute("pagevo", pagevo);
			model.addAttribute("result", new PageMaker<>(result));
			
			// 게시글 목록
			PageVO pagevoCm = new PageVO(1, 6, null, null);
			Page<CommunityEntity> resultCm = communityService.selectAll(pagevoCm);
			model.addAttribute("boardResult", resultCm);
			model.addAttribute("result", new PageMaker<>(resultCm));
			
	      return "index";
	   }
	   
	   @RequestMapping("/layout/about")
	   public void about() {
		   
	   }
	   
	   @RequestMapping("/layout/destination")
	   public void destination() {
		   
	   }
	   
	   @RequestMapping("/layout/hotel")
	   public void hotel() {
		   
	   }
	   
	   @RequestMapping("/layout/blog")
	   public void blog() {
		   
	   }
	   
	   @RequestMapping("/layout/myPage")
	   public void mypage() {}

	   @RequestMapping("/community/boardlist") // /layout/blog 에서 변경
	   public void community() {}  // blog()->community()
	   
	   @RequestMapping("/layout/contact")
	   public void contact() {
		   
	   }
	   
	   @RequestMapping("/layout/login")
	   public void login() {
		   
	   }

	   @RequestMapping("/layout/signup")
	   public void signup() {
		   
	   }
	   

		
}