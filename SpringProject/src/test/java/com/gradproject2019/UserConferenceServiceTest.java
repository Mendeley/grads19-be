package com.gradproject2019;

import com.gradproject2019.auth.persistence.Token;
import com.gradproject2019.auth.service.AuthService;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.persistence.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import com.gradproject2019.conferences.service.ConferenceService;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.persistence.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import com.gradproject2019.userConference.service.UserConferenceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.gradproject2019.conferences.data.ConferenceResponseDto.ConferenceResponseDtoBuilder.aConferenceResponseDto;
import static com.gradproject2019.userConference.persistence.UserConference.UserConferenceBuilder.anUserConference;
import static java.time.Instant.now;
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
    private ConferenceService conferenceService;

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

    @Test
    public void shouldGetConferenceUserIsInterestedIn() {
        UserConference userConference = anUserConference()
                .withUserId(1L)
                .withConferenceId(1L)
                .build();

        ConferenceResponseDto conference = aConferenceResponseDto().withCity("Leciester").withDateTime(now()).withDescription("Hello").withId(1L).withName("Test").withTopic("home").build();

        final List<UserConference> conferencesByUserId = List.of(userConference);
        given(userConferenceRepository.findByUserId(1L)).willReturn(conferencesByUserId);
        given(conferenceService.getConferenceById(1L)).willReturn(conference);

        List<ConferenceResponseDto> conferences = userConferenceService.getConferenceByUserId(token.getToken(), userConference.getUserId());

        assertThat(conferences).hasSize(1);
        assertThat(conferences.get(0).getId()).isEqualTo(userConference.getConferenceId());
    }
}
