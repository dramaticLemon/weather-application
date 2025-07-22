package com.dch.compilers.repositories;

import org.springframework.stereotype.Repository;

import com.dch.compilers.models.User;

@Repository
public class UserRepository {
	
	// @PersistenceContext
	// EntityManager entityManager;
	

	
	public void save(User user) {
		// entityManager.persist(user);
		System.out.println("savig use: " + user);
	}
}
