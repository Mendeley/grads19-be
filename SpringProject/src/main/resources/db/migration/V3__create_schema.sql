CREATE TABLE tokens (
    user_id BIGINT UNSIGNED,
    token CHAR(36),
    FOREIGN KEY (user_id) REFERENCES users(id)
);