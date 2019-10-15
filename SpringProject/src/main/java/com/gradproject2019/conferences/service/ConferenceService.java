package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.persistance.Conference;

import java.util.List;
import java.util.Optional;

public interface ConferenceService {

    List<Conference> listConferences();

    Optional<Conference> findConferenceById(Long conferenceId);

    Conference saveConference(Conference conference);
}