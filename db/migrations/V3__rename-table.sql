ALTER TABLE session rename TO sessions;

ALTER TABLE sessions DROP CONSTRAINT IF EXISTS fk_session_user;

ALTER TABLE sessions
ADD CONSTRAINT fk_sessions_user_id 
FOREIGN KEY (user_id)
REFERENCES users (user_id)
ON DELETE CASCADE;

ALTER TABLE sessions ADD CONSTRAINT uk_sessions_user_id UNIQUE (user_id);