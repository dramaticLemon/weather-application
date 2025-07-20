package com.dch.compilers.repositories;

import org.springframework.stereotype.Repository;

import com.dch.compilers.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	

	
	public void save(User user) {
		entityManager.persist(user);
	}
}
