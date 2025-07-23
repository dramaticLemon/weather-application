package com.dch.compilers.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dch.compilers.dto.UserDto;
import com.dch.compilers.models.User;
import com.dch.compilers.repositories.UserRepository;
import com.dch.compilers.util.PasswordHasher;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

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

	// @SuppressWarnings("unused")
	// private UserDto maptoUserDto(User user) {
	// 	UserDto userDto = new UserDto.Builder()
	// 		.id(user.getUserId())
	// 		.username(user.getUsername())
	// 		.password(user.getPassword())
	// 		.build();
	// 	return userDto;
	// }
}
