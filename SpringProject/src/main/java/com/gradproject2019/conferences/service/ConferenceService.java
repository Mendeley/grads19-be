package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.persistance.Conference;

import java.util.List;

public interface ConferenceService {

    List<Conference> listConferences();

    Conference findConferenceById(Long conferenceId);

    Conference saveConference(Conference conference);
}