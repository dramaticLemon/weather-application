package com.dch.compilers.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.dch.compilers.dto.WeatherDto;
import com.dch.compilers.models.Location;
import com.dch.compilers.services.AuthService;
import com.dch.compilers.services.LocationService;
import com.dch.compilers.services.UserService;

@Controller
public class HomePageConroller {

	private static final Logger log = LoggerFactory.getLogger(HomePageConroller.class);


	@Autowired
	private LocationService locationService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@GetMapping("/dashboard")
	public String register(@CookieValue("SESSION_ID") UUID sessionId, Model model) {
	
		List<Location> userLocations = userService.getUserLocation(sessionId);

		List<WeatherDto> listWeatherDtos = new ArrayList<>();
		for (Location location : userLocations) {
			listWeatherDtos.add(locationService.getWeatherInfo(location.getLatitude(), location.getLongitude()));
		}
		log.info("weather list dto: {} ", listWeatherDtos);
        return "dashboard"; 
	}
}
