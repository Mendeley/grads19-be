package gradproject2019.userConference.service;

import gradproject2019.auth.service.AuthServiceImpl;
import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.conferences.service.ConferenceServiceImpl;
import gradproject2019.userConference.data.UserConferenceRequestDto;
import gradproject2019.userConference.data.UserConferenceResponseDto;
import gradproject2019.userConference.exception.UserAlreadyInterestedException;
import gradproject2019.userConference.persistence.UserConference;
import gradproject2019.userConference.repository.UserConferenceRepository;

import gradproject2019.users.exception.UserForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserConferenceServiceImpl implements UserConferenceService {

    private UserConferenceRepository userConferenceRepository;
    private AuthServiceImpl authServiceImpl;
    private ConferenceServiceImpl conferenceServiceImpl;

    public UserConferenceServiceImpl(UserConferenceRepository userConferenceRepository, AuthServiceImpl authServiceImpl, @Lazy ConferenceServiceImpl conferenceServiceImpl) {
        this.userConferenceRepository = userConferenceRepository;
        this.authServiceImpl = authServiceImpl;
        this.conferenceServiceImpl = conferenceServiceImpl;
    }

    @Override
    public UserConferenceResponseDto saveInterest(UUID token, UserConferenceRequestDto userConferenceRequestDto) {
        UserConference userConference = new UserConference().from(userConferenceRequestDto);

        checkUserMatchesUserConference(authServiceImpl.getTokenById(token).getUserId(), userConference.getUserId());
        conferenceServiceImpl.getConferenceById(userConference.getConferenceId());
        if (userConferenceExists(userConference.getUserId(), userConference.getConferenceId())) {
            throw new UserAlreadyInterestedException();
        }
        return new UserConferenceResponseDto().from(userConferenceRepository.saveAndFlush(userConference));
    }

    @Override
    public void deleteInterest(UUID token, Long conferenceId) {
        Long userId = authServiceImpl.getTokenById(token).getUserId();
        if (!userConferenceExists(userId, conferenceId)) {
            throw new UserConferenceNotFoundException();
        }
        userConferenceRepository.deleteById(userId, conferenceId);
    }

    @Override
    public List<ConferenceResponseDto> getConferenceByUserId(UUID token) {
        Long userId = authServiceImpl.getTokenById(token).getUserId();
        return userConferenceRepository.findByUserId(userId).stream()
                .map(userConference -> conferenceServiceImpl.getConferenceById(userConference.getConferenceId()))
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

    public void deleteByConferenceId(Long conferenceId) {
        userConferenceRepository.deleteByConferenceId(conferenceId);
    }
}