package com.gradproject2019.conferences.service;

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
    public List<Conference> listConferences() {
        return conferenceRepository.findAll();
    }

    @Override
    public Optional<Conference> findConferenceById(Long conferenceId) {
        return conferenceRepository.findById(conferenceId);
    }
}