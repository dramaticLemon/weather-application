package com.dch.compilers.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

	@Column(name="user_id")
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private long user_id;
	
	@Column(name="expires_at")
	private LocalDateTime expiresAt;

	public Session() {}

	public Session(long user_id, LocalDateTime expiresAt) {
		this.user_id = user_id;
		this.expiresAt = expiresAt;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
	
}
