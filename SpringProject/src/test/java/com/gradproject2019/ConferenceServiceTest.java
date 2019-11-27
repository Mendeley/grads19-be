package com.gradproject2019;

import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import com.gradproject2019.conferences.service.ConferenceServiceImpl;
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

    private final Conference conference1 = new Conference(1L, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace");
    private final Conference conference2 = new Conference(2L, "Sophia's conference", Instant.now(), "London", "All about Sophia's fabulous and extra house", "grace");

    @Test
    public void shouldGetListOfAllConferences() {
        given(conferenceRepository.findAll()).willReturn(List.of(conference1, conference2));

        Iterable<ConferenceResponseDto> conferences = conferenceService.getAllConferences();

        assertThat(conferences)
                .extracting(ConferenceResponseDto::getId, ConferenceResponseDto::getName, ConferenceResponseDto::getCity)
                .containsExactly(tuple(conference1.getId(), conference1.getName(), conference1.getCity()), tuple(conference2.getId(), conference2.getName(), conference2.getCity()));
    }

    @Test
    public void shouldGetConferenceById() {
        given(conferenceRepository.findById(1L)).willReturn(Optional.of(conference1));

        ConferenceResponseDto conferenceById = conferenceService.getConferenceById(1L);

        assertThat(conferenceById.getId()).isEqualTo(1L);
        assertThat(conferenceById.getName()).isEqualTo(conference1.getName());
    }

    @Test(expected = ConferenceNotFoundException.class)
    public void shouldThrowErrorWhenIdNotRecognised() {
        conferenceService.getConferenceById(1000000000L);
    }
}