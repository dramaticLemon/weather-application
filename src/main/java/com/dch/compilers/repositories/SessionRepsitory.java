package com.dch.compilers.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.dch.compilers.models.Session;
import com.dch.compilers.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class SessionRepsitory {

	@PersistenceContext
	EntityManager entityManager;

	public Session save(Session session) {
		entityManager.persist(session);
		return session;
	}

	public Optional<Session> findByUser(User user) {
		try {
			TypedQuery<Session> query = entityManager.createQuery(
				"SELECT s FROM Session s WHERE s.user = :user", Session.class);
				query.setParameter("user", user);
				return Optional.of(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	public Optional<Session> findById(UUID uuid) {
		try {
			TypedQuery<Session> query = entityManager.createQuery(
				"SELECT s FROM Session s WHERE s.id = :uuid", Session.class);
				query.setParameter("uuid", uuid);
			return Optional.of(query.getSingleResult());
		 } catch (NoResultException e) {
            return Optional.empty();
        }
	}
}
