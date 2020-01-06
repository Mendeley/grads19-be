package gradproject2019.userConference.service;

import gradproject2019.auth.exception.TokenNotFoundException;
import gradproject2019.auth.exception.UserUnauthorisedException;
import gradproject2019.auth.service.AuthService;
import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.conferences.service.ConferenceService;
import gradproject2019.userConference.data.UserConferenceRequestDto;
import gradproject2019.userConference.data.UserConferenceResponseDto;
import gradproject2019.userConference.exception.UserAlreadyInterestedException;
import gradproject2019.userConference.persistence.UserConference;
import gradproject2019.userConference.repository.UserConferenceRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public List<ConferenceResponseDto> getConferenceByUserId(UUID token, Long userId) {
        checkUserAuthorised(token);
        List<ConferenceResponseDto> conferenceResponseDtos = new ArrayList<>();
        List<UserConference> userConferences = userConferenceRepository.findByUserId(userId);

        for (UserConference userConference : userConferences) {
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