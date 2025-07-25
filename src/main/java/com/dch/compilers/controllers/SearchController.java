package com.dch.compilers.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dch.compilers.dto.CityDto;
import com.dch.compilers.services.SearchCityService;

@Controller
public class SearchController {
	private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private SearchCityService searchCityService;

	@GetMapping("/search-city")
	public String searchCity(@RequestParam("city") String city, Model model) {
		log.info("input city is {}", city);

		List<CityDto> cityList = searchCityService.searchCity(city);
		log.info("list finded cities is {}", cityList);
		
		model.addAttribute("cities", cityList);
		return "dashboard";
	}

	
}
