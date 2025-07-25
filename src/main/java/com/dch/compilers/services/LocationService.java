package com.dch.compilers.services;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dch.compilers.dto.LocationDto;
import com.dch.compilers.models.Location;
import com.dch.compilers.models.User;
import com.dch.compilers.repositories.LocationRepository;

@Service
public class LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Value("${weather.api.url}")
	private String apiUrl;
	@Value("${weather.api.key}")
	private String apiKey;
	@Value("${weather.api.limit}")
	private int limit;

	private final RestTemplate restTemplate = new RestTemplate();

	public List<LocationDto> searchCity(String city) {
		URI uri = UriComponentsBuilder
                .fromUri(URI.create(apiUrl))
                .queryParam("q", city)
                .queryParam("limit", limit)
                .queryParam("appid", apiKey)
                .build()
                .encode()
                .toUri();

		ResponseEntity<LocationDto[]> response = restTemplate.getForEntity(uri, LocationDto[].class);
        LocationDto[] cityArray = response.getBody();

        return cityArray != null ? Arrays.asList(cityArray) : Collections.emptyList();
	}
	
	public Location createLocation(String name, Double latitude, Double longitude) {
		Location newLocation = new Location(name, latitude, longitude);
		return locationRepository.save(newLocation);
	}

	public Location findOrCreateLocation(LocationDto locationDto) {
        return locationRepository.findByCoordinate(locationDto.getLatitude(), locationDto.getLongitude())
                .orElseGet(() -> {
                    Location newLocation = new Location(locationDto.getName(), locationDto.getLatitude(), locationDto.getLongitude());
                    return locationRepository.save(newLocation);
                });
    }

	public List<Location> getAllLocationsForUser(User user) {
		return null;
	}


}
