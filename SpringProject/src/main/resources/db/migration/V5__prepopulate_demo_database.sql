INSERT INTO conferences(name, date_time, city, description, topic)
    VALUES('Festival of Marketing', '2020-11-12 12:34:11', 'London', 'From Festivalofmarketing.com: The Festival of Marketing is a unique experience where ambitious marketers can discover, learn, celebrate and shape the future together. As the largest global event dedicated to brand marketers, the Festival reflects the very nature of ...', 'Marketing'),
    ('Royal Parks Half Marathon 2019', '2020-10-12 12:34:11', 'Manchester', 'From Meetingneeds.org.uk: HOW TO BECOME A CHARITY PARTNER AND WHAT’S IN IT FOR YOUR ORGANISATION? As a Charity Partner, you get to be a central part of the Events Industry charity and share in our growth story. Leveraging your engagement with Meeting Needs a', 'Sport'),
    ('Capital Markets Innovation Summit 2020', '2020-10-19 12:34:11', 'Leeds', 'From Wbresearch.com: Discover how Europe’s leading investment banks are embracing innovation to transform their operating model to move beyond mandatory regulatory change and optimise profitability. Attracting 150+ operations and innovations decision makers ..', 'Innovation'),
    ('About CCR', '2020-10-26 17:34:11', 'Liverpool', 'From Easyfairs.com: The CCR conference and exhibition is supported by leading medical aesthetic organisations who co-locate their events to bring all specialities together under one roof; the British Association of Aesthetic Plastic Surgeons (BAAPS), the', 'Medical'),
    ('Freedom To Operate Conference', '2020-10-30 11:34:11', 'London', 'From 10times.com: The Freedom To Operate Conference is a platform to understand the background, defining terms and objectives; fundamentals of FTO search scope, methods for searching for claimed features, using basic legal status tools to assist .', 'Legal');

INSERT INTO users(username, first_name, last_name, email, password, occupation)
    VALUES("qwerty", "Qwerty", "Test", "qwertytest@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("qwertyA", "Qwerty", "Alpha", "qwertyalpha@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("qwertyG", "Qwerty", "Gamma", "qwertygamma@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("qwertyO", "Qwerty", "Omega", "qwertyomega@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("qwertyD", "Qwerty", "Delta", "qwertydelta@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("MMerrett", "Michael", "Merrett", "m.merrett@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
    ("KKapoor", "Karam", "Kapoor", "k.kapoor@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Dev Ops"),
    ("SMontesBolah", "Sophia", "Montes Bolah", "s.montesbolah@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
    ("GBurleyJones", "Grace", "Burley-Jones", "g.burleyjones@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
    ("KZamaniPickford", "Karen", "Zamani-Pickford", "k.zamani-pickford@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
    ("manager", "Manager", "Test", "managertest@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo Manager");

SET @manager = (SELECT id FROM users WHERE username = "manager");

INSERT INTO users(username, first_name, last_name, email, password, occupation, manager_id)
    VALUES("qwertyWithManager", "Qwerty", "With Manager", "qwertywithmanager@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo Employee", @manager),
    ("qwertyWithManager2", "Qwerty2", "With Manager", "qwerty2withmanager@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo Employee", @manager),
    ("qwertyWithManager3", "Qwerty3", "With Manager", "qwerty3withmanager@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo Employee", @manager);

SET @conference1 = (SELECT id FROM conferences WHERE topic = "Marketing");
SET @conference2 = (SELECT id FROM conferences WHERE topic = "Sport");
SET @user_with_conferences = (SELECT id FROM users WHERE username = "qwerty");

INSERT INTO user_conferences(user_id, conference_id)
    VALUES(@user_with_conferences, @conference1),
    (@user_with_conferences, @conference2);