package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.data.ConferencePatchRequestDto;
import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;

import java.util.List;
import java.util.UUID;

public interface ConferenceService {

    List<ConferenceResponseDto> getAllConferences();

    ConferenceResponseDto getConferenceById(Long conferenceId);

    ConferenceResponseDto saveConference(UUID token, ConferenceRequestDto conferenceRequestDto);

    void deleteConference(UUID token, Long conferenceId);

    ConferenceResponseDto editConference(UUID token, Long conferenceId, ConferencePatchRequestDto conferencePatchRequestDto);
}