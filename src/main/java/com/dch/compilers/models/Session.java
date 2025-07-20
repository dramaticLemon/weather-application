package com.dch.compilers.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="sessions")
public class Session {
	@Id
	@GeneratedValue(generator="UUID")
	@GenericGenerator(
		name="UUID",
		strategy="org.hibernate.id.UUIDGenerator"
	)
	@Column(name="session_id", updatable=false, nullable=false)
	private UUID id;

	@OneToOne
	@MapsId
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Column(name="expires_at")
	private LocalDateTime expiresAt;

	public Session() {}

	public Session(User user, LocalDateTime expiresAt) {
		this.user = user;
		this.expiresAt = expiresAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
	
}
