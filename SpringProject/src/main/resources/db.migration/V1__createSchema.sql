create schema conferenceFinder;
create table conferenceFinder.conference (
	id long,
    conferenceName varchar(255),
    conferenceDateTime datetime,
    city varchar(255),
    conferenceDescription varchar(1000),
    conferenceTopic varchar(255));

CREATE TABLE USERS (ID INT AUTO_INCREMENT PRIMARY KEY, USERID VARCHAR(45));
INSERT INTO USERS (ID, USERID) VALUES (1, 'tutorialspoint.com');