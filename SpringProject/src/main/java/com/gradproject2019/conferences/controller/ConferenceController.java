package com.gradproject2019.conferences.controller;

import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping(path = "/conferences")
    @ResponseBody
    public Iterable<Conference> getAllUsers() {
        return conferenceService.listConferences();
    }

    @GetMapping(path = "/conferences/{id}")
    @ResponseBody
    public Conference getConferenceById(@PathVariable(required = false) Long conferenceId) {
        return conferenceService.findConferenceById(conferenceId);
    }

}