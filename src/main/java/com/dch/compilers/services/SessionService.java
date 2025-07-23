package com.dch.compilers.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dch.compilers.models.Session;
import com.dch.compilers.models.User;
import com.dch.compilers.repositories.SessionRepository;

@Service
public class SessionService {

	@Autowired
	SessionRepository sessionRepository;

	@Value("${session.expire.hours:1}")
	private int sessinExpireHours;
	
	@Value("${session.expire.minutes:1}")
	private int sessionExpireMinutes;

	@Transactional
	public Session registerSession(User user) {
		// LocalDateTime expireAt = LocalDateTime.now().plusHours(sessinExpireHours);
		LocalDateTime expireAt = LocalDateTime.now().plus(sessionExpireMinutes, ChronoUnit.MINUTES);

		Session session = new Session(user, expireAt);
		return sessionRepository.save(session);
	}

	@Transactional
	public Session createOrUpdateSession(User user) {
		Optional<Session> existingSession = sessionRepository.findByUser(user);
		Session session;
		if (existingSession.isPresent()) {
			session = existingSession.get();
		} else {
			session = new Session();
			session.setUser(user);
		}

		// session.setExpiresAt(LocalDateTime.now().plusHours(sessinExpireHours));
		session.setExpiresAt(LocalDateTime.now().plus(sessionExpireMinutes, ChronoUnit.MINUTES));

		try {
			return sessionRepository.save(session);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			 return sessionRepository.findByUser(user)
                               .orElseThrow(() -> new RuntimeException("Failed to retrieve existing session after integrity violation.", e));
		}
		
	}

	public Optional<Session> findByUUID(String sessionId) {
		try {
			return sessionRepository.findById(UUID.fromString(sessionId));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}

    public boolean isSessionValid(String sessionId) {
		Optional<Session> sessionOptional = findByUUID(sessionId);
		if (sessionOptional.isPresent()) {
			Session session = sessionOptional.get();
			return isExpireTimeValid(session.getExpiresAt());
		}
		return false;
    }

	private boolean isExpireTimeValid(LocalDateTime expireAt) {
		return LocalDateTime.now().isBefore(expireAt);
	}

}
