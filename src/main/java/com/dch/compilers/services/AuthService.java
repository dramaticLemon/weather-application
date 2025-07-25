package com.dch.compilers.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dch.compilers.dto.UserDto;
import com.dch.compilers.models.Session;
import com.dch.compilers.models.User;

@Service
public class AuthService {

	@Autowired
    private UserService userService;

	@Autowired
    private SessionService sessionService;

	@Transactional 
    public Session registerNewUserAndCreateSession(UserDto userDto) {
        User newUser = userService.registerUser(userDto); 
        Session userSession = sessionService.createOrUpdateSession(newUser); 
        
        return userSession;
    }
	
	@Transactional
    public Session authenticateAndCreateSession(String username, String password) {
        User authenticatedUser = userService.authenticateUser(username, password);
        Session userSession = sessionService.createOrUpdateSession(authenticatedUser);

        return userSession;
    }


    /*
     * fetch current session user
     */
    public User getCurrentUser(UUID sessionID) {
        Optional<Session> session = sessionService.findByUUID(String.valueOf(sessionID));
        return session.get().getUser();
    }

}
