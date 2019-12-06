package com.gradproject2019;

import com.gradproject2019.auth.persistance.Token;
import com.gradproject2019.auth.service.AuthService;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.persistance.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import com.gradproject2019.userConference.service.UserConferenceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static com.gradproject2019.userConference.persistance.UserConference.UserConferenceBuilder.anUserConference;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserConferenceServiceTest {
    @InjectMocks
    private UserConferenceServiceImpl userConferenceService;

    @Mock
    private UserConferenceRepository userConferenceRepository;

    @Mock
    private AuthService authService;

    private final Token token = new Token(1L, UUID.randomUUID());
    private final UserConferenceRequestDto interest = new UserConferenceRequestDto(1L, 1L);

    @Test
    public void shouldSaveInterestInConference() {
        UserConference userConference = anUserConference()
                .withUserId(1L)
                .withConferenceId(1L)
                .build();
        given(userConferenceRepository.saveAndFlush(any(UserConference.class))).willReturn(userConference);

        UserConferenceResponseDto userConferenceResponseDto = userConferenceService.saveInterest(token.getToken(), interest);

        assertThat(userConference.getConferenceId()).isEqualTo(userConferenceResponseDto.getConferenceId());
        assertThat(userConference.getUserId()).isEqualTo(userConferenceResponseDto.getUserId());

    }
}
