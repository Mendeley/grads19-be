package com.gradproject2019;

import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.repository.AuthRepository;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.exception.UserAlreadyInterestedException;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import com.gradproject2019.userConference.service.UserConferenceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static com.gradproject2019.userConference.data.UserConferenceRequestDto.from;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserConferenceServiceTest {
    @InjectMocks
    private UserConferenceServiceImpl userConferenceService;

    @Mock
    private UserConferenceRepository userConferenceRepository;

    @Mock
    private AuthRepository authRepository;

    private final Token token = new Token(1L, UUID.randomUUID());
    private final UserConferenceRequestDto interest = new UserConferenceRequestDto(1L, 1L);


    @Test(expected = UserAlreadyInterestedException.class)
    public void shouldNotSaveDuplicateInterestInDatabase() {
        given(authRepository.saveAndFlush(token)).willReturn(token);
        given(userConferenceRepository.saveAndFlush(from(interest))).willReturn(from(interest));
        UserConferenceResponseDto userConferenceResponseDto = userConferenceService.saveInterest(token.getToken(), interest1);
    }
}
