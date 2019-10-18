package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.exception.ConferenceConflictException;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.exception.ConferencesNotFoundException;
import com.gradproject2019.conferences.persistance.Conference;

import java.util.List;
import java.util.Optional;

public interface ConferenceService {

    List<Conference> listConferences() /*throws ConferencesNotFoundException*/;

    Conference findConferenceById(Long conferenceId) throws ConferenceNotFoundException;

    Conference saveConference(Conference conference) throws ConferenceConflictException;
}