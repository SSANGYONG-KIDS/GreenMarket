package com.ssangyong.GreenMarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {
	@GetMapping("/hello")
	public void sample1(Model model) {
		model.addAttribute("greeting","하이~~");
	}
}
