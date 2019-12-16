package com.gradproject2019.userConference.service;

import com.gradproject2019.auth.exception.TokenNotFoundException;
import com.gradproject2019.auth.exception.UserUnauthorisedException;
import com.gradproject2019.auth.service.AuthService;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.persistence.Conference;
import com.gradproject2019.conferences.service.ConferenceService;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.exception.UserAlreadyInterestedException;
import com.gradproject2019.userConference.persistence.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserConferenceServiceImpl implements UserConferenceService {

    private UserConferenceRepository userConferenceRepository;
    private AuthService authService;
    private ConferenceService conferenceService;

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
        List<ConferenceResponseDto> conferenceResponseDtos = new ArrayList<>();

        checkUserAuthorised(token);
        List<UserConference> userConferences = userConferenceRepository.findByUserId(userId);

        for(UserConference userConference: userConferences) {
            conferenceResponseDtos.add(conferenceService.getConferenceById(userConference.getConferenceId()));
        }
        return conferenceResponseDtos;
    }

    private void checkUserAuthorised(UUID token) {
        try {
            authService.checkTokenExists(token);
        } catch (TokenNotFoundException e) {
            throw new UserUnauthorisedException();
        }
    }
}