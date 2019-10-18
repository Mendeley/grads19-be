package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.exception.ConferenceConflictException;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.exception.ConferencesNotFoundException;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

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
    public Conference saveConference(Conference conference) throws ConferenceConflictException {
        if(conferenceAlreadyExists) {
            throw new ConferenceConflictException();
        }
        return conferenceRepository.saveAndFlush(conference);
    }
}