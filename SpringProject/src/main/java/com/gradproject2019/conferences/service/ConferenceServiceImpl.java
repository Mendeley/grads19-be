package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.exception.ConferenceConflictException;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.exception.ConferencesNotFoundException;
import com.gradproject2019.conferences.exception.InvalidConferenceFieldException;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<Conference> listConferences() /*throws ConferencesNotFoundException*/ {
        List<Conference> conferences = conferenceRepository.findAll();
        //if(conferences.isEmpty()){
        //    throw new ConferencesNotFoundException();
        //}
        return conferences;
    }

    @Override
    public Conference findConferenceById(Long conferenceId) throws ConferenceNotFoundException {
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
            return optionalConference.orElseThrow(() -> new ConferenceNotFoundException());

    }
    @Override
    public Conference saveConference(Conference conference) throws ConferenceConflictException, InvalidConferenceFieldException {
        if(conference.getId() == null || conference.getName() == null || conference.getDateTime() == null || conference.getCity() == null || conference.getDescription() == null || conference.getTopic() == null){
            throw new InvalidConferenceFieldException();
        }
        if(conferenceRepository.existsById(conference.getId())) {
            throw new ConferenceConflictException();
        } else if(!conference.getDateTime().isAfter(Instant.now())){
            throw new InvalidConferenceFieldException(); //fails other tests!!!
        }
        return conferenceRepository.saveAndFlush(conference);
    }
}