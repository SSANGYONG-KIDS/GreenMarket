package com.ssangyong.GreenMarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	   @RequestMapping("/index")
	   public void main() {
	      System.out.println("main");
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
