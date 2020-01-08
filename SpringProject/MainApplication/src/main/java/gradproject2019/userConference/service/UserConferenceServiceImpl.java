package gradproject2019.userConference.service;

import gradproject2019.auth.exception.TokenNotFoundException;
import gradproject2019.auth.exception.UserUnauthorisedException;
import gradproject2019.auth.service.AuthService;
import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.conferences.service.ConferenceService;
import gradproject2019.userConference.data.UserConferenceRequestDto;
import gradproject2019.userConference.data.UserConferenceResponseDto;
import gradproject2019.userConference.exception.UserAlreadyInterestedException;
import gradproject2019.userConference.exception.UserConferenceNotFoundException;
import gradproject2019.userConference.persistence.UserConference;
import gradproject2019.userConference.repository.UserConferenceRepository;
import gradproject2019.users.exception.UserForbiddenException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserConferenceServiceImpl implements UserConferenceService {

    private UserConferenceRepository userConferenceRepository;
    private AuthService authService;
    private ConferenceService conferenceService;

    public UserConferenceServiceImpl(UserConferenceRepository userConferenceRepository, AuthService authService, ConferenceService conferenceService) {
        this.userConferenceRepository = userConferenceRepository;
        this.authService = authService;
        this.conferenceService = conferenceService;
    }

    @Override
    public UserConferenceResponseDto saveInterest(UUID token, UserConferenceRequestDto userConferenceRequestDto) {
        UserConference userConference = new UserConferenceRequestDto().from(userConferenceRequestDto);

        checkUserAuthorised(token);

        try {
            UserConference savedUserConference = userConferenceRepository.saveAndFlush(userConference);
            new UserConferenceResponseDto();
            return UserConferenceResponseDto.from(savedUserConference);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyInterestedException();
        }
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


    private void checkUserAuthorised(UUID token) {
        try {
            authService.checkTokenExists(token);
        } catch (TokenNotFoundException e) {
            throw new UserUnauthorisedException();
        }
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