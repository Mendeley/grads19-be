package com.gradproject2019.userConference.controller;

import com.gradproject2019.userConference.data.UserConferenceRequestDto;
import com.gradproject2019.userConference.data.UserConferenceResponseDto;
import com.gradproject2019.userConference.service.UserConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class UserConferenceController {

    @Autowired
    private UserConferenceService userConferenceService;

    @PostMapping(path = "/user-conferences")
    public ResponseEntity<UserConferenceResponseDto> saveInterest(@RequestHeader("Authorization") UUID token,
                                                                  @RequestBody UserConferenceRequestDto userConferenceRequestDto) {
        UserConferenceResponseDto newInterest = userConferenceService.saveInterest(token, userConferenceRequestDto);
        return ResponseEntity.ok(newInterest);
    }

    @DeleteMapping(path = "/user-conferences/{userId}/{conferenceId}")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") UUID token, @PathVariable("userId") Long userId, @PathVariable("conferenceId") Long conferenceId) {
        userConferenceService.deleteInterest(token, userId, conferenceId);
        return ResponseEntity.noContent().build();
    }
}