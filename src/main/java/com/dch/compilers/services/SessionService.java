package com.dch.compilers.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

	@Transactional
	public Session createOrUpdateSession(User user) {
		Optional<Session> existingSession = sessionRepsitory.findByUser(user);
		Session session;
		if (existingSession.isPresent()) {
			session = existingSession.get();
		} else {
			session = new Session();
			session.setUser(user);
		}

		session.setExpiresAt(LocalDateTime.now().plusHours(sessinExpireHours));

		return sessionRepsitory.save(session);
	}

	// найти сессию по UUID корорый был записан в куки
	public Optional<Session> findByUUID(String sessionId) {
		try {
			return sessionRepsitory.findById(UUID.fromString(sessionId));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}

}
