USE conference_finder;

DROP TABLE IF EXISTS conferences;

create TABLE conferences (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    date_time DATETIME,
    city VARCHAR(255),
    description TEXT,
    topic VARCHAR(255)
);

INSERT INTO conferences(name, date_time, city, description, topic)
    VALUES('Young biologysts', now(), 'London', 'Dummy description for London', 'biology'),
            ('Manchester tomorrow', now(), 'Manchester', 'Dummy description for Manchester', 'science'),
            ('From Paris with Love', now(), 'Paris', 'Dummy description for Paris', 'science'),
            ('Munich football club', now(), 'Munich', 'Dummy description for Munich', 'sport'),
            ('Berlin syndrome', now(), 'Berlin', 'Dummy description for Berlin', 'science'),
            ('Madrid monitor', now(), 'Madrid', 'Dummy description for Madrid', 'science'),
            ('Nice cocktail bars', now(), 'Nice', 'Dummy description for Nice', 'chemistry'),
            ('Chisinau nightlife', now(), 'Chisinau', 'Dummy description for Chisinau', 'science'),
            ('Kyiv post', now(), 'Kyiv', 'Dummy description for Kyiv', 'medicine'),
            ('Moscow never sleeps', now(), 'Moscow', 'Dummy description for Moscow', 'science');