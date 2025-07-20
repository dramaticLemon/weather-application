package com.dch.compilers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

	@GetMapping("/auth/sign-up")
	public String register(Model model) {
		return "sign-up";
	}

	@PostMapping("auth/sign-up")
	public String processRegistration(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		Model model) {
			model.addAttribute("username", username);
			return "home";
		}
}
