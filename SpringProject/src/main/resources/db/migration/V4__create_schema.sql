CREATE TABLE user_conferences (
    PRIMARY KEY (user_id, conference_id),
    user_id BIGINT UNSIGNED,
    conference_id BIGINT UNSIGNED,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (conference_id) REFERENCES conferences(id)
);
