package com.gradproject2019.userConference.service;

import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserConferenceService {

    UserConferenceResponseDto saveInterest(UUID token, UserConferenceRequestDto userConferenceRequestDto);

    void deleteInterest(UUID token, Long userId, Long conferenceId);
}