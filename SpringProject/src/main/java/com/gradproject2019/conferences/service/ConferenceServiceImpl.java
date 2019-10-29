package com.gradproject2019.conferences.service;

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

    private ConferenceRepository conferenceRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<ConferenceResponseDto> listConferences() {
        return conferenceRepository.findAll().stream()
                .map(conference -> new ConferenceResponseDto().from(conference))
                .collect(Collectors.toList());
    }

    @Override
    public ConferenceResponseDto findConferenceById(Long conferenceId) {
        return new ConferenceResponseDto().from(conferenceRepository
                .findById(conferenceId)
                .orElseThrow(ConferenceNotFoundException::new));
    }

    @Override
    public ConferenceResponseDto saveConference(ConferenceRequestDto conferenceRequestDto){
        Conference conference = conferenceRequestDto.from(conferenceRequestDto);
        checkNotInPast(conference);
        return new ConferenceResponseDto().from(conferenceRepository.saveAndFlush(conference));
    }

    private void checkNotInPast(Conference conference) {
        if (!conference.getDateTime().isAfter(Instant.now())) {
            throw new InvalidConferenceFieldException();
        }
    }

    @Override
    public void deleteConference(Long conferenceId) {
        checkConferenceExists(conferenceId);
        conferenceRepository.deleteById(conferenceId);
    }

    private void checkConferenceExists(Long conferenceId) {
        if(!conferenceRepository.existsById(conferenceId)) {
            throw new ConferenceNotFoundException();
        }
    }
}