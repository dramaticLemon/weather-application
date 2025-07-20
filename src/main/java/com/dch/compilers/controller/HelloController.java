package com.dch.compilers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	
	@GetMapping("/hello")
	public String sayHello(Model model) {
		model.addAttribute("message", "Hello, template!");
		return "hello";
	}
}
