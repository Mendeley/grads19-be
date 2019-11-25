CREATE TABLE tokens (
    user_id BIGINT UNSIGNED,
    token CHAR(36) NOT NULL PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users(id)
);