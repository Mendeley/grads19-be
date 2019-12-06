package com.gradproject2019.userConference.service;

import com.gradproject2019.auth.exception.TokenNotFoundException;
import com.gradproject2019.auth.exception.UserUnauthorisedException;
import com.gradproject2019.auth.service.AuthService;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.exception.UserAlreadyInterestedException;
import com.gradproject2019.userConference.persistance.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserConferenceServiceImpl implements UserConferenceService {

    private UserConferenceRepository userConferenceRepository;
    private AuthService authService;

    public UserConferenceServiceImpl(UserConferenceRepository userConferenceRepository, AuthService authService) {
        this.userConferenceRepository = userConferenceRepository;
        this.authService = authService;
    }

    @Override
    public UserConferenceResponseDto saveInterest(UUID token, UserConferenceRequestDto userConferenceRequestDto) {
        UserConference userConference = new UserConferenceRequestDto().from(userConferenceRequestDto);

        checkUserAuthorised(token);

        try {
            UserConference savedUserConference = userConferenceRepository.saveAndFlush(userConference);
            return new UserConferenceResponseDto().from(savedUserConference);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyInterestedException();
        }
    }

    private void checkUserAuthorised(UUID token) {
        try {
            authService.checkTokenExists(token);
        } catch (TokenNotFoundException e) {
            throw new UserUnauthorisedException();
        }
    }
}