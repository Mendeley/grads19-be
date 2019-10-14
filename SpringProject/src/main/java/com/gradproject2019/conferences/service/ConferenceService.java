package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.persistance.Conference;

public interface ConferenceService {

    Iterable<Conference> listConferences();

    Conference findConferenceById(Long conferenceId);
}
