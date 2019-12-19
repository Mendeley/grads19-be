package com.gradproject2019.userConference.service;

import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserConferenceService {

    UserConferenceResponseDto saveInterest(UUID token, UserConferenceRequestDto userConferenceRequestDto);

    List<ConferenceResponseDto> getConferenceByUserId(UUID token, Long userId);

    void deleteInterest(UUID token, Long userId, Long conferenceId);
}