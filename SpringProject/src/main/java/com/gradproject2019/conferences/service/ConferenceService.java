package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.persistance.Conference;

import java.util.List;

public interface ConferenceService {

    List<ConferenceResponseDto> listConferences();

    ConferenceResponseDto findConferenceById(Long conferenceId);

    ConferenceResponseDto saveConference(ConferenceRequestDto conferenceRequestDto);
}