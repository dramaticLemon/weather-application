package com.dch.compilers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageConroller {
	@GetMapping("/dashboard")
	public String register(Model model) {
        return "dashboard"; 
	}
}
