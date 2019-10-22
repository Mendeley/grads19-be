package com.gradproject2019;

import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import com.gradproject2019.conferences.service.ConferenceServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceServiceTest {

    @InjectMocks
    private ConferenceServiceImpl conferenceService;

    @Mock
    private ConferenceRepository conferenceRepository;

    @Test
    public void shouldGetListOfAllConferences() {
        // GIVEN
        Conference conference1 = new Conference(1L, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace");
        Conference conference2 = new Conference(2L, "Sophia's conference", Instant.now(), "London", "All about Sophia's fabulous and extra house", "grace");
        given(conferenceRepository.findAll()).willReturn(List.of(conference1, conference2));

        // WHEN
        Iterable<Conference> conferences = conferenceService.listConferences();

        // THEN
        assertThat(conferences)
                .extracting(Conference::getId, Conference::getName, Conference::getCity)
                .containsExactly(tuple(conference1.getId(), conference1.getName(), conference1.getCity()), tuple(conference2.getId(), conference2.getName(), conference2.getCity()));
    }

    @Test
    public void shouldGetConferenceById() {
        //given
        Long conferenceId = 1L;
        Conference conference = new Conference(conferenceId, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace");
        given(conferenceRepository.findById(conferenceId)).willReturn(Optional.of(conference));

        //when
        Conference conferenceById = conferenceService.findConferenceById(conferenceId);

        //then
        assertThat(conferenceById.getId()).isEqualTo(conferenceId);
        assertThat(conferenceById.getName()).isEqualTo(conference.getName());
        assertThat(conferenceById.getCity()).isEqualTo(conference.getCity());
        assertThat(conferenceById.getTopic()).isEqualTo(conference.getTopic());
    }

    @Test
    public void shouldThrowErrorWhenIdNotRecognised() {
        //given
        Long conferenceId = 1000000000L;
        boolean errorThrown = false;

        //when
        try {
            conferenceService.findConferenceById(conferenceId);
        } catch(ConferenceNotFoundException e) {
            errorThrown = true;
        }

        //then
        Assert.assertTrue(errorThrown);
    }
}