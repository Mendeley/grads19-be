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
            return conferenceRepository.findById(conferenceId).orElseThrow(() -> new ConferenceNotFoundException());
    }
    @Override
    public Conference saveConference(Conference conference) {
        if(conference.getId() == null || conference.getName() == null || conference.getDateTime() == null || conference.getCity() == null || conference.getDescription() == null || conference.getTopic() == null){
            throw new InvalidConferenceFieldException();
        }
        if(conferenceRepository.existsById(conference.getId())) {
            throw new ConferenceAlreadyExistsException();
        } else if(!conference.getDateTime().isAfter(Instant.now())){
            throw new InvalidConferenceFieldException();
        }
        return conferenceRepository.saveAndFlush(conference);
    }
}