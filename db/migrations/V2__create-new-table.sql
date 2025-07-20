CREATE TABLE user_locations (
    user_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, location_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (location_id) REFERENCES locations(location_id)
);