package com.gradproject2019.userConferences.service;

import com.gradproject2019.userConferences.data.UserConferenceRequestDto;
import com.gradproject2019.userConferences.data.UserConferenceResponseDto;
import com.gradproject2019.userConferences.persistance.UserConference;
import com.gradproject2019.userConferences.repository.UserConferenceRepository;


import static com.gradproject2019.userConferences.data.UserConferenceRequestDto.from;

public class UserConferenceServiceImpl  implements UserConferenceService{

    private final UserConferenceRepository userConferenceRepository;

    public UserConferenceServiceImpl(UserConferenceRepository userConferenceRepository) {
        this.userConferenceRepository = userConferenceRepository;

    }
    @Override
    public UserConferenceResponseDto saveInterest(UserConferenceRequestDto userConferenceRequestDto) {

        UserConference userConference = from(userConferenceRequestDto);
        //check if already interested

        return new UserConferenceResponseDto().from(userConferenceRepository.saveAndFlush(userConference));
    }

}
