package com.gradproject2019;

import com.gradproject2019.auth.persistence.Token;
import com.gradproject2019.auth.service.AuthServiceImpl;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.persistence.Conference;
import com.gradproject2019.conferences.service.ConferenceServiceImpl;
import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.persistence.UserConference;
import com.gradproject2019.userConference.repository.UserConferenceRepository;
import com.gradproject2019.userConference.service.UserConferenceServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
    private AuthServiceImpl authServiceImpl;

    @Mock
    private ConferenceServiceImpl conferenceServiceImpl;

    private final Token token = new Token(1L, UUID.randomUUID());
    private final UserConferenceRequestDto userConferenceRequestDto = new UserConferenceRequestDto(1L, 1L);
    private final UserConference userConference = new UserConference(1L, 1L);
    private final Conference conference = new Conference(1L, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace");
    private final ConferenceResponseDto conferenceResponseDto = new ConferenceResponseDto().from(conference);

    @Test
    public void shouldSaveInterestInConference() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        given(conferenceServiceImpl.getConferenceById(userConference.getConferenceId())).willReturn(conferenceResponseDto);
        given(userConferenceRepository.saveAndFlush(any(UserConference.class))).willReturn(userConference);

        UserConferenceResponseDto userConferenceResponseDto = userConferenceService.saveInterest(token.getToken(), userConferenceRequestDto);

        assertThat(userConference.getConferenceId()).isEqualTo(userConferenceResponseDto.getConferenceId());
        assertThat(userConference.getUserId()).isEqualTo(userConferenceResponseDto.getUserId());
    }

    @Test
    public void shouldGetConferenceUserIsInterestedIn() {
        given(authServiceImpl.getTokenById(token.getToken())).willReturn(token);
        given(userConferenceRepository.findByUserId(userConference.getUserId())).willReturn(List.of(userConference));
        given(conferenceServiceImpl.getConferenceById(userConference.getConferenceId())).willReturn(conferenceResponseDto);

        List<ConferenceResponseDto> conferenceResponseDtoList = userConferenceService.getConferenceByUserId(token.getToken(), userConference.getUserId());

        Assert.assertEquals(conferenceResponseDtoList.size(), 1);
        assertThat(conferenceResponseDtoList.get(0).getId()).isEqualTo(userConference.getConferenceId());
    }
}
