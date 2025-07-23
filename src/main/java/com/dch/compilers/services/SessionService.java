package com.dch.compilers.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dch.compilers.models.Session;
import com.dch.compilers.models.User;
import com.dch.compilers.repositories.SessionRepsitory;

@Service
public class SessionService {

	@Autowired
	SessionRepsitory sessionRepsitory;

	@Value("${session.expire.hours:1}")
	private int sessinExpireHours;
	
	@Transactional
	public Session registerSession(User user) {
		LocalDateTime expireAt = LocalDateTime.now().plusHours(sessinExpireHours);
		Session session = new Session(user, expireAt);
		return sessionRepsitory.save(session);
	}
}
