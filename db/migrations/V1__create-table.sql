CREATE TABLE users (
	user_id SERIAL primary key,
	username VARCHAR(255) NOT NULL,
	hash_password VARCHAR(255) NOT NULL
);

CREATE TABLE locations (
	location_id SERIAL primary key,
	name_location VARCHAR(255)  NULL,
	user_id BIGINT NOT NULL,
	lititude DOUBLE PRECISION,
	longitude DOUBLE PRECISION,
	CONSTRAINT fk_user
		FOREIGN KEY (user_id)
		REFERENCES users(user_id)
		ON DELETE CASCADE
);

CREATE TABLE session (
	session_id UUID PRIMARY KEY,
	user_id BIGINT NOT NULL,
	expires_at TIMESTAMP,
	CONSTRAINT fk_session_user
		FOREIGN KEY(user_id)
		REFERENCES users(user_id)
		ON DELETE CASCADE
);