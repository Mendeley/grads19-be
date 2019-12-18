package gradproject2019.conferences.service;

import gradproject2019.conferences.data.ConferencePatchRequestDto;
import gradproject2019.conferences.data.ConferenceRequestDto;
import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.conferences.persistance.Conference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ConferenceService {

    List<ConferenceResponseDto> getAllConferences();

    ConferenceResponseDto getConferenceById(Long conferenceId);

    ConferenceResponseDto saveConference(UUID token, ConferenceRequestDto conferenceRequestDto);

    void deleteConference(UUID token, Long conferenceId);

    ConferenceResponseDto editConference(UUID token, Long conferenceId, ConferencePatchRequestDto conferencePatchRequestDto);

    //List<ConferenceResponseDto> searchConferences(String name, String city, String description, String topic, Pageable pageable);

    //Page<Conference> findByName(String name, Pageable pageable);

    //Page<Conference> findByCity(String city, Pageable pageable);

    //Page<Conference> findByDescription(String description, Pageable pageable);

    List<ConferenceResponseDto> findByConferenceTopic(String topic, Integer page, Integer size);
}