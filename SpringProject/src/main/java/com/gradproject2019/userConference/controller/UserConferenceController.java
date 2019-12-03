package com.gradproject2019.userConference.controller;

import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.service.UserConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class UserConferenceController {

    @Autowired
    private UserConferenceService userConferenceService;

    @PostMapping(path = "/{id}")
    public ResponseEntity<UserConferenceResponseDto> saveInterest(@Valid @RequestHeader("Authorization") UUID token, @RequestBody UserConferenceRequestDto userConferenceRequestDto) {
        UserConferenceResponseDto newInterest = userConferenceService.saveInterest(userConferenceRequestDto, token);
        return ResponseEntity.ok(newInterest);
    }

}
