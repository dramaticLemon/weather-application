package com.dch.compilers.services;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dch.compilers.dto.CityDto;

@Service
public class SearchCityService {

	@Value("${weather.api.url}")
	private String apiUrl;
	@Value("${weather.api.key}")
	private String apiKey;
	@Value("${weather.api.limit}")
	private int limit;

	private final RestTemplate restTemplate = new RestTemplate();

	public List<CityDto> searchCity(String city) {
		URI uri = UriComponentsBuilder
                .fromUri(URI.create(apiUrl))
                .queryParam("q", city)
                .queryParam("limit", limit)
                .queryParam("appid", apiKey)
                .build()
                .encode()
                .toUri();

		ResponseEntity<CityDto[]> response = restTemplate.getForEntity(uri, CityDto[].class);
        CityDto[] cityArray = response.getBody();

        return cityArray != null ? Arrays.asList(cityArray) : Collections.emptyList();
	}
	
}
