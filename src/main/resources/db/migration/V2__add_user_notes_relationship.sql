ALTER TABLE notes ADD COLUMN user_id BIGINT;

ALTER TABLE notes
ADD CONSTRAINT fk_notes_user_id
FOREIGN KEY (user_id) REFERENCES users(id);