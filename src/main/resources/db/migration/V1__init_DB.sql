CREATE SEQUENCE IF NOT EXISTS seq_note_id
    START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS seq_user_id
    START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS notes
(
    id BIGINT DEFAULT nextval('seq_note_id'),
    title VARCHAR(255),
    content TEXT,
    CONSTRAINT pk_notes_id PRIMARY KEY(id),
    CONSTRAINT chk_title_length CHECK (LENGTH(title) BETWEEN 3 AND 255)
);

CREATE TABLE IF NOT EXISTS users
(
     id BIGINT DEFAULT nextval('seq_user_id'),
     username VARCHAR(50) UNIQUE NOT NULL,
     password VARCHAR(255) NOT NULL,
     enabled BOOLEAN NOT NULL DEFAULT true,
     CONSTRAINT pk_users_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id BIGSERIAL,
    name VARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT pk_roles_id PRIMARY KEY(id)

);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id BIGINT,
    role_id BIGINT,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users_roles_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_users_roles_role_id FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE INDEX users_roles_user_idx ON users_roles(user_id);
CREATE INDEX users_roles_role_idx ON users_roles(role_id);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');