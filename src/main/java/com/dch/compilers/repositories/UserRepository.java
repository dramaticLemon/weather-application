package com.dch.compilers.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dch.compilers.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class UserRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public User save(User user) {
		entityManager.persist(user);
		return user;
	}


	public Optional<User> findByUserName(String username) {
		try {
			TypedQuery<User> query = entityManager.createQuery(
				"SELECT u FROM User u WHERE u.username = :username", User.class);
				query.setParameter("username", username);
			return Optional.of(query.getSingleResult());
		 } catch (NoResultException e) {
            return Optional.empty();
        }
	}

	public User findUserWithLocations(Long id) {
		String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.locations WHERE u.id = :id";
		return entityManager.createQuery(jpql, User.class)
			.setParameter("id", id)
			.getSingleResult();
	}
}
