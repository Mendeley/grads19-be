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