package com.gradproject2019;

import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import com.gradproject2019.conferences.service.ConferenceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceTests {

    @InjectMocks
    ConferenceServiceImpl conferenceService;

    @Mock
    ConferenceRepository conferenceRepository;

    @Test
    public void shouldGetAllConferences(){
        conferenceService.listConferences();

        verify(conferenceRepository).findAll();
    }

    @Test
    public void shouldGetConferencesById(){
        //given
        Long conferenceId = 1L;
        given(conferenceRepository.findById(conferenceId)).willReturn(Optional.of(new Conference(conferenceId, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace" )));

        //when
        Conference conference = conferenceService.findConferenceById(conferenceId);

        //then
        assertThat(conference.getId()).isEqualTo(conferenceId);
        assertThat(conference.getName()).isEqualTo("Grace's conference");
        assertThat(conference.getCity()).isEqualTo("Leicester");
        assertThat(conference.getTopic()).isEqualTo("grace");
    }
}
