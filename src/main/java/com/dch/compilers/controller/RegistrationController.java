package com.dch.compilers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dch.compilers.dto.UserDto;
import com.dch.compilers.models.Session;
import com.dch.compilers.models.User;
import com.dch.compilers.services.SessionService;
import com.dch.compilers.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService sessionService;

	@GetMapping("/auth/sign-up")
	public String register(Model model) {
		return "sign-up";
	}

	@PostMapping("/auth/sign-up")
	public String processRegistration(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("repeat-password") String repeatPassword, HttpSession httpSession, Model model, HttpServletResponse response) {

		if (!password.equals(repeatPassword)) {
			model.addAttribute("error", "Passwords don't match, please try again.");
			return "sign-up";
		}

		UserDto userDto = new UserDto.Builder()
			.username(username)
			.password(password)
			.build();
			
		User user = userService.registerUser(userDto);
		Session session = sessionService.registerSession(user);

		Cookie cookie = new Cookie("SESSION_ID", String.valueOf(session.getId()));
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);

		return "redirect:/dashboard";
		}
}
