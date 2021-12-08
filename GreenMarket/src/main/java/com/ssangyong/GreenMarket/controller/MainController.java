package com.ssangyong.GreenMarket.controller;

import java.util.ArrayList;
import java.util.List;

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
	   public String main(Model model, HttpServletRequest request, ItemPageVO pagevo) {
	     
		  System.out.println("main 실행");
		  // main에 게시물 6개까지 보여주기
	      int numOfItem = 6;
	      List<ItemEntity> resultAll = itemService.selectAll();
	      List<ItemEntity> result = new ArrayList<>();
	      for (int i =0; i < numOfItem; i++) {
	    	  result.add(resultAll.get(i));
	      }
	      
//	      model.addAttribute("pagevo", pagevo);
//	      model.addAttribute("result", new PageMaker<>(result));
	      model.addAttribute("itemResult", result);
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