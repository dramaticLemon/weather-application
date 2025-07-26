package com.dch.compilers.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dch.compilers.dto.LocationDto;
import com.dch.compilers.dto.UserDto;
import com.dch.compilers.models.Location;
import com.dch.compilers.models.Session;
import com.dch.compilers.models.User;
import com.dch.compilers.repositories.SessionRepository;
import com.dch.compilers.repositories.UserRepository;
import com.dch.compilers.util.PasswordHasher;

@Service
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
	LocationService locationService;
	@Autowired
	SessionRepository sessionRepository;

	@Transactional
	public User registerUser(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(PasswordHasher.hashPassword(userDto.getPassword()));
		return userRepository.save(user);
	}

	public Optional<User> findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Transactional(readOnly=true)
	public User authenticateUser(String username, String rawPassword) {
		Optional<User> userOptional = findByUserName(username);

		if (userOptional.isEmpty()) {
			throw new IllegalArgumentException("User not found.");
		} 
		User user =userOptional.get();
		if (!PasswordHasher.checkPassword(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Uncorrect password."); 
        }
		return user;
	}


	@Transactional
	public List<Location> addLocationToUser(UUID sessionID, LocationDto locationDto) {
		Optional<Session> session = sessionRepository.findById(sessionID);
		User user = userRepository.findUserWithLocations(session.get().getUser().getUserId());

		Location location = locationService.findOrCreateLocation(locationDto);
		user.getLocations().add(location);
		userRepository.save(user);

		return new ArrayList<>(user.getLocations());
	}

	public List<Location> getUserLocation(UUID sessionID) {
		Optional<Session> session = sessionRepository.findById(sessionID);
		User user = userRepository.findUserWithLocations(session.get().getUser().getUserId());
		return new ArrayList<>(user.getLocations());
	}

	@Transactional
	public void removeLocationForUser(UUID sessionID, double latitude, double longitude) {
		Optional<Session> session = sessionRepository.findById(sessionID);
		User user = userRepository.findUserWithLocations(session.get().getUser().getUserId());
		Optional<Location> location = locationService.findByCoordinate(latitude, longitude);
		if (location.isPresent() && user != null) {
			user.getLocations().remove(location.get());
			userRepository.save(user);
			log.info("remove user location {}", location.get());
			
			// check if other user bind  this location and remove if condition
			locationService.removeUnusedLocationsAsync(location.get());

		}


	}
}
