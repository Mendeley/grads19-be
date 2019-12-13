package com.gradproject2019.userConference.service;

import com.gradproject2019.auth.exception.TokenNotFoundException;
import com.gradproject2019.auth.exception.UserUnauthorisedException;
import com.gradproject2019.auth.service.AuthService;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.exception.UserAlreadyInterestedException;
import com.gradproject2019.userConference.persistence.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<ConferenceResponseDto> getConferenceByUserId(UUID token, Long userId) {

        checkUserAuthorised(token);
        //use userId to return a list of conference Ids
        List<UserConference> conferenceId = UserConferenceResponseDto.from(userConferenceRepository.findByUserId(userId));
        //use the list of conference Ids to get the conferences
        //return list of conferences
        //return UserConferenceResponseDto.from(userConferenceRepository.findByUserId(userId));
        return null;
    }

    private void checkUserAuthorised(UUID token) {
        try {
            authService.checkTokenExists(token);
        } catch (TokenNotFoundException e) {
            throw new UserUnauthorisedException();
        }
    }
}