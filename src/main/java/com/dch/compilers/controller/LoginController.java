package com.dch.compilers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dch.compilers.models.Session;
import com.dch.compilers.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("/auth/sign-in")
	public String sayHello(Model model) {
		return "sign-in";
	}

	@PostMapping("/auth/sign-in")
	public String processRegistration(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		HttpSession httpSession,
		Model model,
		HttpServletResponse response) 
		{
		
			try {
				Session userSession = authService.authenticateAndCreateSession(username, password);

				Cookie sessionCookie = new Cookie("SESSION_ID", userSession.getId().toString());
				sessionCookie.setHttpOnly(true);
            	sessionCookie.setPath("/");
				response.addCookie(sessionCookie);

				return "redirect:/dashboard";
			} catch (IllegalArgumentException e) {
				model.addAttribute("error", e.getMessage());
				return "sign-in";
			} catch (Exception e) {
				model.addAttribute("error", "Произошла ошибка при входе. Пожалуйста, попробуйте позже.");
            	return "sign-in";
			}

		}
}
