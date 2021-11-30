package com.ssangyong.GreenMarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssangyong.GreenMarket.model.ICategoryEnumType;

@Controller
public class MainController {
	
	   @RequestMapping(value = {"/", "/index"})
	   public String main(Model model) {
	      System.out.println("main");
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
	   
	   @RequestMapping("/layout/contact")
	   public void contact() {}
	   
	   @RequestMapping("/layout/login")
	   public void login() {}

	   @RequestMapping("/layout/signup")
	   public void signup() {}
}
