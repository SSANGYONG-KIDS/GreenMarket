package com.ssangyong.GreenMarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/main")
	public void main() {
		System.out.println("main");
	}

	@RequestMapping("/main25")
	public void main22() {
		System.out.println("main22 method");
	}
}
