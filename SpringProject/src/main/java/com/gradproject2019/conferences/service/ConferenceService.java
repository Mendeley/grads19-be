package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.data.ConferencePatchRequestDto;
import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;

import java.util.List;

public interface ConferenceService {

    List<ConferenceResponseDto> getAllConferences();

    ConferenceResponseDto getConferenceById(Long conferenceId);

    ConferenceResponseDto saveConference(ConferenceRequestDto conferenceRequestDto);

    void deleteConference(Long conferenceId);

    ConferenceResponseDto editConference(Long conferenceId, ConferencePatchRequestDto conferencePatchRequestDto);
}