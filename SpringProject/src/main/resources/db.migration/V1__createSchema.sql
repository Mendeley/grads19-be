create schema conferenceFinder;
create table conferenceFinder.conference (
	id long,
    conferenceName varchar(255),
    conferenceDateTime datetime,
    city varchar(255),
    conferenceDescription varchar(1000),
    conferenceTopic varchar(255));