package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.exception.ConferenceAlreadyExistsException;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.exception.InvalidConferenceFieldException;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<Conference> listConferences() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference findConferenceById(Long conferenceId) {
            return conferenceRepository.findById(conferenceId).orElseThrow(ConferenceNotFoundException::new);
    }
    @Override
    public Conference saveConference(Conference conference) {
        isNotNull(conference);
        isNotExisting(conference);
        isNotInPast(conference);
        return conferenceRepository.saveAndFlush(conference);
    }

    public void isNotNull(Conference conference) {
        if(conference.getName() == null || conference.getDateTime() == null || conference.getCity() == null || conference.getDescription() == null || conference.getTopic() == null){
            throw new InvalidConferenceFieldException();
        }
    }

    public void isNotExisting(Conference conference) {
        if(conferenceRepository.existsById(conference.getId())) {
            throw new ConferenceAlreadyExistsException();
        }
    }

    public void isNotInPast(Conference conference) {
        if(!conference.getDateTime().isAfter(Instant.now())){
            throw new InvalidConferenceFieldException();
        }
    }
}