package gradproject2019.userConference.controller;

import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.userConference.data.UserConferenceRequestDto;
import gradproject2019.userConference.data.UserConferenceResponseDto;
import gradproject2019.userConference.service.UserConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/user-conferences")
public class UserConferenceController {

    @Autowired
    private UserConferenceService userConferenceService;

    @PostMapping
    public ResponseEntity<UserConferenceResponseDto> saveInterest(@RequestHeader("Authorization") UUID token,
                                                                  @RequestBody UserConferenceRequestDto userConferenceRequestDto) {
        UserConferenceResponseDto newInterest = userConferenceService.saveInterest(token, userConferenceRequestDto);
        return ResponseEntity.ok(newInterest);
    }

    @GetMapping
    public ResponseEntity<List<ConferenceResponseDto>> getConferenceByUserId(@RequestHeader("Authorization") UUID token) {
        List<ConferenceResponseDto> conferences = userConferenceService.getConferenceByUserId(token);
        return ResponseEntity.ok(conferences);
    }

    @DeleteMapping(path = "/{conferenceId}")
    public ResponseEntity<Void> deleteInterest(@RequestHeader("Authorization") UUID token, @PathVariable("conferenceId") Long conferenceId) {
        userConferenceService.deleteInterest(token, conferenceId);
        return ResponseEntity.noContent().build();
    }
}