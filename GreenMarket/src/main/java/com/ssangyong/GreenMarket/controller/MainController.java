package com.ssangyong.GreenMarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //?
@Controller
@RequestMapping 
public class MainController {

	@RequestMapping("/index")
	public void main() {
		System.out.println("main");
	}
}
