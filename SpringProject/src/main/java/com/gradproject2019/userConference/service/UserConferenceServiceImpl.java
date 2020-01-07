package com.gradproject2019.userConference.service;

import com.gradproject2019.auth.service.AuthService;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.service.ConferenceService;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.exception.UserAlreadyInterestedException;
import com.gradproject2019.userConference.exception.UserConferenceNotFoundException;
import com.gradproject2019.userConference.persistence.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import com.gradproject2019.users.exception.UserForbiddenException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserConferenceServiceImpl implements UserConferenceService {

    private UserConferenceRepository userConferenceRepository;
    private AuthService authService;
    private ConferenceService conferenceService;

    public UserConferenceServiceImpl(UserConferenceRepository userConferenceRepository, AuthService authService, @Lazy ConferenceService conferenceService) {
        this.userConferenceRepository = userConferenceRepository;
        this.authService = authService;
        this.conferenceService = conferenceService;
    }

    @Override
    public UserConferenceResponseDto saveInterest(UUID token, UserConferenceRequestDto userConferenceRequestDto) {
        UserConference userConference = new UserConference().from(userConferenceRequestDto);

        checkUserMatchesUserConference(authService.getTokenById(token).getUserId(), userConference.getUserId());
        conferenceService.getConferenceById(userConference.getConferenceId());
        if (userConferenceExists(userConference.getUserId(), userConference.getConferenceId())) {
            throw new UserAlreadyInterestedException();
        }
        return new UserConferenceResponseDto().from(userConferenceRepository.saveAndFlush(userConference));
    }

    @Override
    public void deleteInterest(UUID token, Long conferenceId) {
        Long userId = authService.getTokenById(token).getUserId();
        if (!userConferenceExists(userId, conferenceId)) {
            throw new UserConferenceNotFoundException();
        }
        userConferenceRepository.deleteById(userId, conferenceId);
    }

    @Override
    public List<ConferenceResponseDto> getConferenceByUserId(UUID token) {
        Long userId = authService.getTokenById(token).getUserId();
        return userConferenceRepository.findByUserId(userId).stream()
                .map(userConference -> conferenceService.getConferenceById(userConference.getConferenceId()))
                .collect(Collectors.toList());
    }

    private boolean userConferenceExists(Long userId, Long conferenceId) {
        return userConferenceRepository.exists(userId, conferenceId) > 0;
    }

    private void checkUserMatchesUserConference(Long retrievedId, Long userId) {
        if (!retrievedId.equals(userId)) {
            throw new UserForbiddenException();
        }
    }

    @Override
    public void deleteByConferenceId(Long conferenceId) {
        userConferenceRepository.deleteByConferenceId(conferenceId);
    }
}