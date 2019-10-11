package com.gradproject2019.conferences.controller;

import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@Controller
public class ConferenceController {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @GetMapping(path="/conferences")
    public @ResponseBody Iterable<Conference> getAllUsers(){
        return conferenceRepository.findAll();
    }
}