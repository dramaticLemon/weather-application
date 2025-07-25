package com.dch.compilers.repositories;

import java.util.List;
import java.util.Optional;

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
	
	public Optional<Location> findByCoordinate(double latitude, double longitude) {
        String jpql = "SELECT l FROM Location l WHERE l.latitude = :lat AND l.longitude = :lon";
        List<Location> result = entityManager.createQuery(jpql, Location.class)
                .setParameter("lat", latitude)
                .setParameter("lon", longitude)
                .getResultList();

        return result.stream().findFirst();
    }
}
