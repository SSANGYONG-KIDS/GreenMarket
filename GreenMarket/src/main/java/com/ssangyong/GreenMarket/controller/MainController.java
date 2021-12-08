package com.ssangyong.GreenMarket.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssangyong.GreenMarket.model.ICategoryEnumType;
import com.ssangyong.GreenMarket.model.ItemEntity;
import com.ssangyong.GreenMarket.model.ItemPageVO;
import com.ssangyong.GreenMarket.model.PageMaker;
import com.ssangyong.GreenMarket.service.ItemPhotoService;
import com.ssangyong.GreenMarket.service.ItemService;
import com.ssangyong.GreenMarket.service.LoginService;

@Controller
public class MainController {
	
	   @RequestMapping(value = {"/", "/index"})
	   public String main(Model model) {
	      System.out.println("main");
	      model.addAttribute("itemSorts", ICategoryEnumType.values());
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
	   
	   @RequestMapping("/layout/contact")
	   public void contact() {
		   
	   }
	   
	   @RequestMapping("/layout/login")
	   public void login() {
		   
	   }

	   @RequestMapping("/layout/signup")
	   public void signup() {
		   
	   }
	   
	   @Autowired
		ItemService itemService;
		@Autowired
		ItemPhotoService itemPhotoService;
		@Autowired
		LoginService loginService;
		
}