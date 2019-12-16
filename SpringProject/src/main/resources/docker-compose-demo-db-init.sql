USE conference_finder;

DROP TABLE IF EXISTS user_conferences;
DROP TABLE IF EXISTS conferences;
DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS users;

CREATE TABLE conferences (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    date_time DATETIME,
    city VARCHAR(255),
    description TEXT,
    topic VARCHAR(255)
);

CREATE TABLE users (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    password CHAR(60),
    occupation VARCHAR(255),
    manager_id BIGINT UNSIGNED
);

CREATE TABLE tokens (
    user_id BIGINT UNSIGNED,
    token CHAR(36) NOT NULL PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE user_conferences (
    PRIMARY KEY (user_id, conference_id),
    user_id BIGINT UNSIGNED,
    conference_id BIGINT UNSIGNED,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (conference_id) REFERENCES conferences(id)
);

INSERT INTO conferences(name, date_time, city, description, topic)
VALUES('Festival of Marketing', '2020-11-12 12:34:11', 'London', 'From Festivalofmarketing.com: The Festival of Marketing is a unique experience where ambitious marketers can discover, learn, celebrate and shape the future together. As the largest global event dedicated to brand marketers, the Festival reflects the very nature of ...', 'Marketing'),
('Royal Parks Half Marathon 2019', '2020-10-12 12:34:11', 'Manchester', 'From Meetingneeds.org.uk: HOW TO BECOME A CHARITY PARTNER AND WHAT’S IN IT FOR YOUR ORGANISATION? As a Charity Partner, you get to be a central part of the Events Industry charity and share in our growth story. Leveraging your engagement with Meeting Needs a', 'Sport'),
('Capital Markets Innovation Summit 2020', '2020-10-19 12:34:11', 'Leeds', 'From Wbresearch.com: Discover how Europe’s leading investment banks are embracing innovation to transform their operating model to move beyond mandatory regulatory change and optimise profitability. Attracting 150+ operations and innovations decision makers ..', 'Innovation'),
('About CCR', '2020-10-26 17:34:11', 'Liverpool', 'From Easyfairs.com: The CCR conference and exhibition is supported by leading medical aesthetic organisations who co-locate their events to bring all specialities together under one roof; the British Association of Aesthetic Plastic Surgeons (BAAPS), the', 'Medical'),
('Freedom To Operate Conference', '2020-10-30 11:34:11', 'London', 'From 10times.com: The Freedom To Operate Conference is a platform to understand the background, defining terms and objectives; fundamentals of FTO search scope, methods for searching for claimed features, using basic legal status tools to assist .', 'Legal');

INSERT INTO users(username, first_name, last_name, email, password, occupation)
VALUES("qwerty", "qwerty", "qwerty", "qwerty@qwerty.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "qwerty"),
("qwertyButCool", "qwerty", "butcool", "qwertyButCool@qwerty.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "qwertyButCool"),
("JimKirk", "James", "Kirk", "jamestkirk@starfleet.gov", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Captain"),
("Scotty", "Montgomery", "Scott", "scottyknowsbest@starfleet.gov", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Chief Engineer"),
("Bones", "Leonard", "McCoy", "bonesmcoy@starfleet.gov", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "A doctor not a ... Jim!"),
("MMerrett", "Michael", "Merrett", "michaelmerrett@placeholder.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
("Zezima", "Karam", "Kapoor", "zezimarulez69@runescape.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "GOAT"),
("Sophia", "Sophia", "Montes Bolah", "s.montesbolah@placeholder.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
("Grace", "Grace", "Burley-Jones", "g.burleyjones@placeholder.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
("Karen", "Karen", "Zamani-Pickford", "k.zamani-pickford@placeholder.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer");

SET @manager = (SELECT id FROM users WHERE username = "JimKirk");

INSERT INTO users(username, first_name, last_name, email, password, occupation, manager_id)
VALUE("qwertyWithManager", "qwertyWithManager", "qwertyWithManager", "qwertyWithManager@qwertyWithManager.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "qwertyWithManager", @manager);