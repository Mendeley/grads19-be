package com.gradproject2019.userConference.service;

import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;

import java.util.UUID;


public interface UserConferenceService {

    UserConferenceResponseDto saveInterest(UserConferenceRequestDto userConferenceRequestDto, UUID token);
}
