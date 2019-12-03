package com.gradproject2019.userConference.service;

import com.gradproject2019.auth.exception.TokenNotFoundException;
import com.gradproject2019.auth.exception.UserUnauthorisedException;
import com.gradproject2019.auth.service.AuthServiceImpl;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.exception.UserAlreadyInterestedException;
import com.gradproject2019.userConference.persistance.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import org.springframework.stereotype.Service;


import java.util.UUID;

import static com.gradproject2019.userConference.data.UserConferenceRequestDto.from;

@Service
public class UserConferenceServiceImpl  implements UserConferenceService{

    private  UserConferenceRepository userConferenceRepository;
    private  AuthServiceImpl authServiceImpl;

    public UserConferenceServiceImpl(UserConferenceRepository userConferenceRepository, AuthServiceImpl authServiceImpl) {
        this.userConferenceRepository = userConferenceRepository;
        this.authServiceImpl = authServiceImpl;
    }

    @Override
    public UserConferenceResponseDto saveInterest(UserConferenceRequestDto userConferenceRequestDto, UUID token) {
        UserConference userConference = from(userConferenceRequestDto);
        //check if user logged in
        checkUserAuthorised(token);
        //check if already interested



        return new UserConferenceResponseDto().from(userConferenceRepository.saveAndFlush(userConference));
    }

    private void checkUserAuthorised(UUID token) {
        try {
            authServiceImpl.checkTokenExists(token);
        } catch (TokenNotFoundException e) {
            throw new UserUnauthorisedException();
        }
    }

    private void checkUserNotAlreadyInterested(Long userId, Long conferenceId){
        if(userConferenceRepository.findByUserId(userId).isPresent() && userConferenceRepository.findByConferenceId(conferenceId).isPresent())
            throw new UserAlreadyInterestedException();
    }

}
