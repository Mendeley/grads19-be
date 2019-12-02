package com.gradproject2019.userConferences.controller;

import com.gradproject2019.userConferences.data.UserConferenceResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class UserConferencesController {

    @Autowired
    private UserConferencesService userConferencesService;

    @PostMapping(path = "/users/{id}")
    public ResponseEntity<UserConferencesResponseDto> saveInterest(@Valid @RequestHeader("Authorization") @RequestBody UserConferenceRequestedDto userConferenceRequestDto) {
        UserConferenceResponseDto newInterest = userConferencesService.saveInterest(userConferenceRequestDto);
        return ResponseEntity.ok(newInterest);
    }

}
