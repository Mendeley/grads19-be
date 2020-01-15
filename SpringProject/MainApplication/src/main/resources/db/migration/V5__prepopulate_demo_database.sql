INSERT INTO conferences(name, date_time, city, description, topic)
    VALUES('Festival of Marketing', '2020-10-07 10:00:00', 'London', 'The Festival of Marketing is a unique two day experience where ambitious marketers can discover, learn, celebrate and shape the future together. As the largest global event dedicated to brand marketers, the Festival reflects the very nature of marketing – seamlessly blending inspiration and practical application. With amazing headliners, 12 stages of content, insightful speakers and the Festival City brimming with ideas and solutions to your challenges, you will be part of an experience like no other. The Festival of Marketing 2020 takes place 7-8 October at Tobacco Dock, London. Secure your space today!', 'Marketing'),
    ('Royal Parks Half Marathon 2020', '2020-10-11 09:00:00', 'London', 'This multi-award winning, environmentally conscious half marathon was founded in 2008 and now over a decade later, still takes in the same beautiful route through closed roads in the city centre of the city and four of the famous Royal Parks. Starting and finishing in Hyde Park, the race winds through Green Park, St James’s Park and Kensington Gardens, taking in the sights of many of London’s most famous landmarks in the stunning surroundings of the Queen’s back gardens.', 'Sport'),
    ('Synthetic Biology Conference', '2020-07-06 09:00:00', 'London','Synthetic Biology Conference is Europe’s leading international synthetic biology conference for innovators and experts in synthetic biology research, commercialization, investment, and policymaking. It will focus on the greatest opportunities and challenges for building a multibillion-dollar synthetic biology industry that will contribute to the fast-growing bio-economy.','Biology'),
    ('Capital Markets Innovation Summit 2020', '2020-09-22 11:30:00', 'London', 'Over 150 COOs, Heads of Operations and Innovation share successes in using tech innovation in front-to-back operations and compliance projects to drive ROI. Find out how you can leverage AI, DLT, Cloud, RPA, NLT, Fintech, Regtech and much more', 'Innovation'),
    ('RAS Specialist Discussion Meeting','2020-03-13 10:00:00','London','The solar wind creates and controls the heliosphere, within which all the planets move, and it drives societally important space weather effects in near-Earth space and on the planet’s surface.  With the launch in August 2018 of NASA’s Parker Solar Probe, a new era has opened in the exploration of the inner solar system. Probe has already travelled to within 35 solar radii of the Sun, nearly twice as close as any previous mission; by the time of this meeting the first perihelion data will have been public for several months so there will be ample opportunity to discuss the exciting first results on topics such as solar wind kinetics, fine scale structure and the links between solar dynamics and solar wind structures. In February 2020, Solar Orbiter will launch: this meeting will also be a good opportunity to discuss Orbiter science goals in light of the early Parker Solar Probe results.','Astronomy'),
    ('CCR Expo', '2020-10-01 09:00:00', 'London', 'Clinical Cosmetic & Reconstructive Expo provides free-to-attend, CPD accredited education for all levels. This year delivers a clear focus on live demonstrations and injectables. CCR brought the entire industry together in a celebration of branded, inspirational, scientific and world-class content.', 'Medical'),
    ('Freedom To Operate Conference', '2020-02-28 09:00:00', 'London', 'The Freedom To Operate Conference is a platform to understand the background, defining terms and objectives; fundamentals of FTO search scope, methods for searching for claimed features, using basic legal status tools to assist valuation, and understanding and reporting the results from an FTO perspective.', 'Legal'),
    ('AI & Big Data Conference 2020','2020-03-17 09:00:00','London','It’s back! The AI & Big Data Expo Global, the leading Artificial Intelligence & Big Data Conference & Exhibition is taking place on 17-18th March 2020 at the Olympia in London. It will showcase the next generation technologies and strategies from the world of Artificial Intelligence & Big Data, providing an opportunity to explore and discover the practical and successful implementation of AI & Big Data to drive your business forward in 2020 and beyond.','Technology'),
    ('Diversity in Technology Roundtable', '2020-03-17 08:30:00', 'London', 'This breakfast meeting will bring together organisations in the tech industry to share insights and help generate ideas about diversity and inclusion initiatives, looking at what different companies are doing to bring it to the forefront of company strategy.', 'Diversity'),
    ('ICQE 2020', '2020-03-12 09:30:00','London', 'The International Research Conference is a federated organization dedicated to bringing together a significant number of diverse scholarly events for presentation within the conference program. Events will run over a span of time during the conference depending on the number and length of the presentations. ICQE 2020 : International Conference on Quantum Electrodynamics is the premier interdisciplinary forum for the presentation of new advances and research results in the fields of Quantum Electrodynamics. The conference will bring together leading academic scientists, researchers and scholars in the domain of interest from around the world.','Physics');

INSERT INTO users(username, first_name, last_name, email, password, occupation)
    VALUES("demoUserE", "Demo", "User E", "demouserE@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("demoUserA", "Demo", "User A", "demouserA@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("demoUserD", "Demo", "User D", "demouserd@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("demoUserC", "Demo", "User C", "demouserc@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("demoUserB", "Demo", "User B", "demouserb@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("MMerrett", "Michael", "Merrett", "m.merrett@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo User"),
    ("KKapoor", "Karam", "Kapoor", "k.kapoor@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Dev Ops"),
    ("SMontesBolah", "Sophia", "Montes Bolah", "s.montesbolah@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
    ("GBurleyJones", "Grace", "Burley-Jones", "g.burleyjones@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
    ("KZamaniPickford", "Karen", "Zamani-Pickford", "k.zamani-pickford@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Software Engineer"),
    ("managersManager", "Demo", "Manager's Manger", "demomanagersmanager@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo Manager's Manager");

SET @managersManagerId = (SELECT id FROM users WHERE username = "managersManager");

INSERT INTO users(username, first_name, last_name, email, password, occupation, manager_id)
    VALUES("manager", "Demo", "Manager", "demomanager@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo Manager", @managersManagerId);

SET @managerId = (SELECT id FROM users WHERE username = "manager");

INSERT INTO users(username, first_name, last_name, email, password, occupation, manager_id)
    VALUES("demoUserWithManagerA", "Demo User", "With Manager A", "demouserwithmanagera@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo Employee", @managerId),
    ("demoUserWithManagerC", "Demo User", "With Manager C", "demouserwithmanagerc@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo Employee", @managerId),
    ("demoUserWithManagerB", "Demo User", "With Manager B", "demouserwithmanagerb@email.com", "$2a$10$rRbskGuL.nq8B28H0ZbrNOV1KvobJ3zbeoDbeJ5Pdhf8x27EswaY2", "Demo Employee", @managerId);

SET @conference1 = (SELECT id FROM conferences WHERE topic = "Marketing");
SET @conference2 = (SELECT id FROM conferences WHERE topic = "Sport");
SET @conference3 = (SELECT id FROM conferences WHERE topic = "Innovation");

INSERT INTO user_conferences(user_id, conference_id)
    VALUES(@managerId, @conference1),
    (@managerId, @conference2),
    (@managerId, @conference3);