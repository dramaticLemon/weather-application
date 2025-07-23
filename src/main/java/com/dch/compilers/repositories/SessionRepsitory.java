package com.dch.compilers.repositories;

import org.springframework.stereotype.Repository;

import com.dch.compilers.models.Session;

@Repository
public class SessionRepsitory {

	public Session save(Session session) {
		System.out.println("Saving session: " + session);
		return session;
	}
}
