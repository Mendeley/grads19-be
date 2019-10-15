package com.gradproject2019.conferences.controller;

import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping(path = "/conferences")
    public ResponseEntity<List<Conference>> getAllConferences() {
        return ResponseEntity.ok(conferenceService.listConferences());
    }

    @GetMapping(path = "/conferences/{id}")
    public ResponseEntity<Optional<Conference>> getConferenceById(@PathVariable(required = false) Long conferenceId) {
        Optional<Conference> conference = conferenceService.findConferenceById(conferenceId);
        return ResponseEntity.ok(conference);
    }

    @PostMapping(path = "/conferences/newconference")
    public ResponseEntity<Conference> postConference(@PathVariable(required = false) Conference conference) {
        Conference newConference = conferenceService.saveConference(conference);
        return ResponseEntity.ok(newConference);
    }
}