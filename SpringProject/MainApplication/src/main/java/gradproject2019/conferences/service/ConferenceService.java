package gradproject2019.conferences.service;

import gradproject2019.conferences.data.ConferencePatchRequestDto;
import gradproject2019.conferences.data.ConferenceRequestDto;
import gradproject2019.conferences.data.ConferenceResponseDto;

import java.util.List;
import java.util.UUID;

public interface ConferenceService {

    List<ConferenceResponseDto> getAllConferences();

    ConferenceResponseDto getConferenceById(Long conferenceId);

    ConferenceResponseDto saveConference(UUID token, ConferenceRequestDto conferenceRequestDto);

    void deleteConference(UUID token, Long conferenceId);

    ConferenceResponseDto editConference(UUID token, Long conferenceId, ConferencePatchRequestDto conferencePatchRequestDto);

    List<ConferenceResponseDto> findByConferenceName(String name, Integer page, Integer size);

    List<ConferenceResponseDto> findByConferenceCity(String city, Integer page, Integer size);

    List<ConferenceResponseDto> findByConferenceDescription(String description, Integer page, Integer size);

    List<ConferenceResponseDto> findByConferenceTopic(String topic, Integer page, Integer size);
}