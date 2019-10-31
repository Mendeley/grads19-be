package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.data.ConferencePatchRequestDto;
import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.exception.InvalidConferenceFieldException;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    @Override
    public ConferenceResponseDto editConference(Long conferenceId, ConferencePatchRequestDto conferencePatchRequestDto){
        checkConferenceExists(conferenceId);
        Conference conference = conferencePatchRequestDto.from(conferenceId, conferencePatchRequestDto);
        checkNotInPast(conference.getDateTime());
        conferenceRepository.updateConference(conference.getId(), conference.getName(), conference.getDateTime(), conference.getCity(), conference.getDescription(), conference.getTopic());
        return getConferenceById(conferenceId);
    }

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<ConferenceResponseDto> getAllConferences() {
        return conferenceRepository.findAll().stream()
                .map(conference -> new ConferenceResponseDto().from(conference))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteConference(Long conferenceId) {
        checkConferenceExists(conferenceId);
        conferenceRepository.deleteById(conferenceId);
    }

    @Override
    public ConferenceResponseDto getConferenceById(Long conferenceId) {
        return new ConferenceResponseDto().from(conferenceRepository
                .findById(conferenceId)
                .orElseThrow(ConferenceNotFoundException::new));
    }

    @Override
    public ConferenceResponseDto saveConference(ConferenceRequestDto conferenceRequestDto){
        Conference conference = conferenceRequestDto.from(conferenceRequestDto);
        checkNotInPast(conference.getDateTime());
        return new ConferenceResponseDto().from(conferenceRepository.saveAndFlush(conference));
    }

    private void checkNotInPast(Instant dateTime) {
        try {
            if (!dateTime.isAfter(Instant.now())) {
                throw new InvalidConferenceFieldException();
            }
        } catch (NullPointerException ignored) {}
    }
    private ConferenceRepository conferenceRepository;

    private void checkConferenceExists(Long conferenceId) {
        if(!conferenceRepository.existsById(conferenceId)) {
            throw new ConferenceNotFoundException();
        }
    }
}