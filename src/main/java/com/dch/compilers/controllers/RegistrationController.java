package com.dch.compilers.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dch.compilers.dto.RegistrationForm;
import com.dch.compilers.dto.UserDto;
import com.dch.compilers.models.Session;
import com.dch.compilers.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;



@Controller
public class RegistrationController {

	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private AuthService authService;

	@GetMapping("/auth/sign-up")
	public String register(Model model) {
		model.addAttribute("form", new RegistrationForm()); 
		return "sign-up";
	}

	@PostMapping("/auth/sign-up")
	public String processRegistration(
		@ModelAttribute("form") @Valid  RegistrationForm form,
		BindingResult bindingResult,
		Model model,
    	HttpServletResponse response

	) {
		if (bindingResult.hasErrors()) {
        return "sign-up";
		}

		if (!form.getPassword().equals(form.getRepeatPassword())) { 
			model.addAttribute("error", "Passwords don't match");
			return "sign-up";
		}
			
		try {
			UserDto userDto = new UserDto.Builder()
			.username(form.getUsername())
			.password(form.getPassword())
			.build();
			
			Session createdSession = authService.registerNewUserAndCreateSession(userDto);

			Cookie cookie = new Cookie("SESSION_ID", String.valueOf(createdSession.getId()));
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);

			return "redirect:/dashboard";

		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			log.debug(e.getMessage());
            return "sign-up";
		} catch (ConstraintViolationException e) {
			model.addAttribute("error", "User name is already taken");
			log.debug("Diplicate username: {}", e.getMessage());
			return "sign-up";
		}
	}
}
