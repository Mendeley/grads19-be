package com.gradproject2019.conferences.service;

import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public Iterable<Conference> listConferences() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference findConferenceById(Long conferenceId) {
        Optional<Conference> conference = conferenceRepository.findById(conferenceId);
        if(conference.isPresent())
            return conference.get();

        return null;
    }
}
