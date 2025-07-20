package com.dch.compilers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dch.compilers.dto.UserDto;
import com.dch.compilers.services.UserService;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/sign-up")
	public String register(Model model) {
		return "sign-up";
	}

	@PostMapping("auth/sign-up")
	public String processRegistration(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		Model model) {

		UserDto userDto = new UserDto.Builder()
			.username(username)
			.password(password)
			.build();
			
		userService.registerUser(userDto);
		return "home";
		}

}
