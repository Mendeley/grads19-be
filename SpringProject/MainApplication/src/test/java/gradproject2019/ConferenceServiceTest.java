package gradproject2019;

import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.conferences.exception.ConferenceNotFoundException;
import gradproject2019.conferences.persistence.Conference;
import gradproject2019.conferences.repository.ConferenceRepository;
import gradproject2019.conferences.service.ConferenceServiceImpl;
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
    private ConferenceServiceImpl conferenceServiceImpl;

    @Mock
    private ConferenceRepository conferenceRepository;

    private static final Conference CONFERENCE_1 = new Conference(1L, "Grace's conference", Instant.now(), "Leicester", "All about Grace's fabulous and extra house", "grace");
    private static final Conference CONFERENCE_2 = new Conference(2L, "Sophia's conference", Instant.now(), "London", "All about Sophia's fabulous and extra house", "grace");

    @Test
    public void shouldGetListOfAllConferences() {
        given(conferenceRepository.findAll()).willReturn(List.of(CONFERENCE_1, CONFERENCE_2));

        List<ConferenceResponseDto> expectedConferences = conferenceServiceImpl.getAllConferences();

        assertThat(expectedConferences)
                .extracting(ConferenceResponseDto::getId, ConferenceResponseDto::getName, ConferenceResponseDto::getCity)
                .containsExactly(tuple(this.CONFERENCE_1.getId(), this.CONFERENCE_1.getName(), this.CONFERENCE_1.getCity()), tuple(CONFERENCE_2.getId(), CONFERENCE_2.getName(), CONFERENCE_2.getCity()));
    }

    @Test
    public void shouldGetConferenceById() {
        given(conferenceRepository.findById(1L)).willReturn(Optional.of(CONFERENCE_1));

        ConferenceResponseDto conferenceById = conferenceServiceImpl.getConferenceById(1L);

        assertThat(conferenceById.getId()).isEqualTo(1L);
        assertThat(conferenceById.getName()).isEqualTo(CONFERENCE_1.getName());
    }

    @Test(expected = ConferenceNotFoundException.class)
    public void shouldThrowErrorWhenIdNotRecognised() {
        conferenceServiceImpl.getConferenceById(Long.MAX_VALUE);
    }
}