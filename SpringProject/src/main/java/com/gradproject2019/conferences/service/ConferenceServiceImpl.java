package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.exception.ConferenceAlreadyExistsException;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.exception.InvalidConferenceFieldException;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<ConferenceResponseDto> listConferences() {
        List<Conference> conferenceList = conferenceRepository.findAll();
        List<ConferenceResponseDto> conferenceResponseDtoList = new ArrayList<>();
        for (Conference conference : conferenceList) {
            conferenceResponseDtoList.add(new ConferenceResponseDto().from(conference));
        }
        return conferenceResponseDtoList;
    }

    @Override
    public ConferenceResponseDto findConferenceById(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId).orElseThrow(ConferenceNotFoundException::new);
        ConferenceResponseDto conferenceResponseDto = new ConferenceResponseDto().from(conference);
        return conferenceResponseDto;
    }

    @Override
    public ConferenceResponseDto saveConference(ConferenceRequestDto conferenceRequestDto){
        Conference conference = conferenceRequestDto.from(conferenceRequestDto);
        isNotInPast(conference);
        Conference conf = conferenceRepository.saveAndFlush(conference);
        ConferenceResponseDto conferenceResponseDto = new ConferenceResponseDto().from(conf);
        return conferenceResponseDto;
    }

    public void isNotInPast(Conference conference) {
        if(!conference.getDateTime().isAfter(Instant.now())){
            throw new InvalidConferenceFieldException();
        }
    }
}