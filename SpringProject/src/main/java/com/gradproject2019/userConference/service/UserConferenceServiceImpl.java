package com.gradproject2019.userConference.service;

import com.gradproject2019.auth.service.AuthServiceImpl;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.exception.UserAlreadyInterestedException;
import com.gradproject2019.userConference.exception.UserConferenceNotFoundException;
import com.gradproject2019.userConference.persistence.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import com.gradproject2019.users.exception.UserForbiddenException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserConferenceServiceImpl implements UserConferenceService {

    private UserConferenceRepository userConferenceRepository;
    private AuthServiceImpl authServiceImpl;

    public UserConferenceServiceImpl(UserConferenceRepository userConferenceRepository, AuthServiceImpl authServiceImpl) {
        this.userConferenceRepository = userConferenceRepository;
        this.authServiceImpl = authServiceImpl;
    }

    @Override
    public UserConferenceResponseDto saveInterest(UUID token, UserConferenceRequestDto userConferenceRequestDto) {
        UserConference userConference = new UserConferenceRequestDto().from(userConferenceRequestDto);

        authServiceImpl.getTokenById(token);

        try {
            UserConference savedUserConference = userConferenceRepository.saveAndFlush(userConference);
            return new UserConferenceResponseDto().from(savedUserConference);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyInterestedException();
        }
    }

    @Override
    public void deleteInterest(UUID token, Long userId, Long conferenceId) {
        Long retrievedId = authServiceImpl.getTokenById(token).getUserId();
        checkUserMatchesUserConference(retrievedId, userId);
        checkUserConferenceExists(userId, conferenceId);
        userConferenceRepository.deleteById(userId, conferenceId);
    }

    private void checkUserConferenceExists(Long userId, Long conferenceId) {
        if (userConferenceRepository.exists(userId, conferenceId) < 1) {
            throw new UserConferenceNotFoundException();
        }
    }

    private void checkUserMatchesUserConference(Long retrievedId, Long userId) {
        if (!retrievedId.equals(userId)) {
            throw new UserForbiddenException();
        }
    }

    public void deleteByConferenceId(Long conferenceId) {
        userConferenceRepository.deleteByConferenceId(conferenceId);
    }

    public boolean existsByConferenceId(Long conferenceId) {
        return userConferenceRepository.existsByConferenceId(conferenceId) >= 1;
    }
}