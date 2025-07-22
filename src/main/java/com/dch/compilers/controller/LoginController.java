package com.dch.compilers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@GetMapping("/auth/sign-in")
	public String sayHello(Model model) {
		return "sign-in";
	}

	@PostMapping("/auth/sign-in")
	public String processRegistration(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		HttpSession httpSession,
		Model model) 
		{
		return "redirect:/dashboard";
		}
}
