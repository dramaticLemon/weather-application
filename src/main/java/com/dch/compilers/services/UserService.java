package com.dch.compilers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dch.compilers.dto.UserDto;
import com.dch.compilers.models.User;
import com.dch.compilers.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	@Transactional
	public void registerUser(UserDto userDto) {
		// logic check password, uniq name user
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		userRepository.save(user);
	}

	// private String encodePassword(String password) {
    //     return password;
    // }

	
	@SuppressWarnings("unused")
	private UserDto maptoUserDto(User user) {
		UserDto userDto = new UserDto.Builder()
			.id(user.getUserId())
			.username(user.getUsername())
			.password(user.getPassword())
			.build();
		return userDto;
	}
}
