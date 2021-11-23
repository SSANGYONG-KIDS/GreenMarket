package com.ssangyong.GreenMarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	   @RequestMapping("/index")
	   public void main() {
	      System.out.println("main");
	   }

}