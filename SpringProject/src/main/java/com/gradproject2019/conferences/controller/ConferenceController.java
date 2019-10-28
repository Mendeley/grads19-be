package com.gradproject2019.conferences.controller;

import com.gradproject2019.conferences.data.ConferenceRequestDto;
import com.gradproject2019.conferences.data.ConferenceResponseDto;
import com.gradproject2019.conferences.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping(path = "/conferences")
    public ResponseEntity<List<ConferenceResponseDto>> getAllConferences() {
        List<ConferenceResponseDto> conferences = conferenceService.listConferences();
        return ResponseEntity.ok(conferences);
    }

    @GetMapping(path = "/conferences/{id}")
    public ResponseEntity<ConferenceResponseDto> findConferenceById(@PathVariable("id") Long conferenceId) {
        ConferenceResponseDto conferenceResponseDto = conferenceService.findConferenceById(conferenceId);
        return ResponseEntity.ok(conferenceResponseDto);
    }

    @PostMapping(path = "/conferences")
    public ResponseEntity<ConferenceResponseDto> saveConference(@Valid @RequestBody ConferenceRequestDto conferenceRequestDto) {
        ConferenceResponseDto newConference = conferenceService.saveConference(conferenceRequestDto);
        return ResponseEntity.ok(newConference);
    }

    @DeleteMapping(path = "/conferences/{id}")
    public ResponseEntity<Void> deleteConference(@PathVariable("id") Long conferenceId) {
        conferenceService.deleteConference(conferenceId);
        return ResponseEntity.noContent().build();
    }
}