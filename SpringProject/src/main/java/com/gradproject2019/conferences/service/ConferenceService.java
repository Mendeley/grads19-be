package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.exception.ConferenceAlreadyExistsException;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.exception.InvalidConferenceFieldException;
import com.gradproject2019.conferences.persistance.Conference;

import java.util.List;

public interface ConferenceService {

    List<Conference> listConferences();

    Conference findConferenceById(Long conferenceId) throws ConferenceNotFoundException;

    Conference saveConference(Conference conference) throws ConferenceAlreadyExistsException, InvalidConferenceFieldException;
}