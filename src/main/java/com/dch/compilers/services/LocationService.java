package com.dch.compilers.services;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dch.compilers.dto.LocationDto;
import com.dch.compilers.dto.WeatherDto;
import com.dch.compilers.manager.RedisManager;
import com.dch.compilers.models.Location;
import com.dch.compilers.models.User;
import com.dch.compilers.repositories.LocationRepository;
import com.dch.compilers.util.meper.ApiWeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class LocationService {

	private static final Logger log = LoggerFactory.getLogger(LocationService.class);

	@Autowired
    private RedisManager redisManager;

	@Autowired
	private LocationRepository locationRepository;

	@Value("${weather.api.url}")
	private String apiUrl;

	@Value("${weather.api.key}")
	private String apiKey;

	@Value("${weather.api.limit}")
	private int limit;

	@Value("${weather.api.url.getWeather}")
	private String apiUrlGetWeahterInfo;

	@Value("${weather.api.units}")
	private String units;


	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper mapper = new ObjectMapper();

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

	public WeatherDto getWeatherInfo(double lat, double lon) {
		String key = "weather:" + lat + ":" + lon;

		try {
            String cached = redisManager.get(key);
            if (cached != null) {
                return mapper.readValue(cached, WeatherDto.class);
            }
        } catch (Exception e) {
            log.debug("cashing: {}", key);
        }

		URI uri = UriComponentsBuilder
                .fromUri(URI.create(apiUrlGetWeahterInfo))
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey) 
				.queryParam("units", units)
                .build()
                .encode()
                .toUri();


		ResponseEntity<ApiWeatherResponse> response = restTemplate.getForEntity(uri, ApiWeatherResponse.class);
		ApiWeatherResponse body = response.getBody();

		if (body == null || body.getWeather() == null || body.getWeather().isEmpty()) {
			throw new RuntimeException("Invalid weather data");
		}

		WeatherDto weatherDto = new WeatherDto(
			body.getName(),
			body.getMain().getTemp(),
			body.getMain().getFeels_like(),
			body.getWeather().get(0).getDescription(),
			body.getWeather().get(0).getIcon(),
			body.getMain().getHumidity(),
			body.getWind().getSpeed(),
			lat,
			lon
		);

		try {
			String json = mapper.writeValueAsString(weatherDto);
			redisManager.set(key, json, 1200);
		} catch (Exception e) {
			log.warn("serialization error {}", e);
		}
		return weatherDto;

	}

	public Optional<Location> findByCoordinate(double latitude, double longitude) {
		return locationRepository.findByCoordinate(latitude, longitude);
	}
}
