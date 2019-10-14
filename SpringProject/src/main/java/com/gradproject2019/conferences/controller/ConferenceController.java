package com.gradproject2019.conferences.controller;

import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping(path = "/conferences")
    public @ResponseBody
    Iterable<Conference> getAllUsers() {
        return conferenceService.listConferences();
    }
}