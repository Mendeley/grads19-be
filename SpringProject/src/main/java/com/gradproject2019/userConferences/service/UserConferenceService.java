package com.gradproject2019.userConferences.service;

import com.gradproject2019.userConferences.data.UserConferenceRequestDto;
import com.gradproject2019.userConferences.data.UserConferenceResponseDto;
import org.springframework.stereotype.Service;


public interface UserConferenceService {

    UserConferenceResponseDto saveInterest(UserConferenceRequestDto userConferenceRequestDto);
}
