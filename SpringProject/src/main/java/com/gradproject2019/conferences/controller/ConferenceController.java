package com.gradproject2019.conferences.controller;

import com.gradproject2019.conferences.exception.ConferenceConflictException;
import com.gradproject2019.conferences.exception.ConferenceNotFoundException;
import com.gradproject2019.conferences.exception.InvalidConferenceFieldException;
import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping(path = "/conferences")
    public ResponseEntity<List<Conference>> getAllConferences() /*throws ConferencesNotFoundException*/ {
        List<Conference> conferences = conferenceService.listConferences();
        return ResponseEntity.ok(conferences);
    }

    @GetMapping(path = "/conferences/{id}")
    public ResponseEntity<Conference> findConferenceById(@PathVariable("id") Long conferenceId) throws ConferenceNotFoundException {
        Conference conference = conferenceService.findConferenceById(conferenceId);
        return ResponseEntity.ok(conference);
    }

    @PostMapping(path = "/conferences")
    public ResponseEntity<Conference> saveConference(@RequestBody Conference conference) throws ConferenceConflictException, InvalidConferenceFieldException {
        Conference newConference = conferenceService.saveConference(conference);
        return ResponseEntity.ok(newConference);
    }
}