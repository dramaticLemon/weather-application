package com.dch.compilers.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dch.compilers.models.Location;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class LocationRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public Location save(Location location) {
		entityManager.persist(location);
		return location;
	}
	
}
