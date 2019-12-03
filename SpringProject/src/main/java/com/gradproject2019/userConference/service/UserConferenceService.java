package com.gradproject2019.userConference.service;

import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserConferenceService {

    UserConferenceResponseDto saveInterest(UserConferenceRequestDto userConferenceRequestDto, UUID token);
}
