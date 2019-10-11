CREATE TABLE conferences (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    date_time DATETIME,
    city VARCHAR(255),
    description TEXT,
    topic VARCHAR(255)
);


