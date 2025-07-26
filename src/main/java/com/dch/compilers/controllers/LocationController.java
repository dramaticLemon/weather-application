package com.dch.compilers.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dch.compilers.dto.LocationDto;
import com.dch.compilers.models.Location;
import com.dch.compilers.services.AuthService;
import com.dch.compilers.services.LocationService;
import com.dch.compilers.services.UserService;

@Controller
public class LocationController {
	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private LocationService locationService;
	@Autowired
	private AuthService authService;
	@Autowired
	private UserService userService;

	@GetMapping("/search-city")
	public String searchCity(@RequestParam("city") String city, Model model) {
		log.info("input city is {}", city);

		List<LocationDto> locationDto = locationService.searchCity(city);
		log.info("list finded cities is {}", locationDto);
		
		model.addAttribute("cities", locationDto);
		return "dashboard";
	}

	@PostMapping("/add-location")
	public String addLocation(
		@ModelAttribute LocationDto locationDto, @CookieValue("SESSION_ID") UUID sessionId, Model model) {

		log.info(
			"taked city cart is : {}", locationDto
		);
		
		List<Location> userLocations = userService.addLocationToUser(sessionId, locationDto);
		log.info("User locations is {} ", userLocations);

		return "redirect:/dashboard";
	}

	@PostMapping("/remove-location")
	public String removeLocation(
			@RequestParam("latitude") double latitude,
			@RequestParam("longitude") double longitude,
			@CookieValue("SESSION_ID") UUID sessionId) {

		userService.removeLocationForUser(sessionId, latitude, longitude);
		return "redirect:/dashboard";
	}
	
} 
