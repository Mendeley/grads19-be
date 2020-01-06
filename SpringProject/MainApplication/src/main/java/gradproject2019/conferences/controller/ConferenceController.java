package gradproject2019.conferences.controller;

import gradproject2019.conferences.data.ConferencePatchRequestDto;
import gradproject2019.conferences.data.ConferenceRequestDto;
import gradproject2019.conferences.data.ConferenceResponseDto;
import gradproject2019.conferences.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping(path = "/conferences")
    public ResponseEntity<List<ConferenceResponseDto>> getAllConferences() {
        List<ConferenceResponseDto> conferences = conferenceService.getAllConferences();
        return ResponseEntity.ok(conferences);
    }

    @GetMapping(path = "/conferences/{id}")
    public ResponseEntity<ConferenceResponseDto> getConferenceById(@PathVariable("id") Long conferenceId) {
        ConferenceResponseDto conferenceResponseDto = conferenceService.getConferenceById(conferenceId);
        return ResponseEntity.ok(conferenceResponseDto);
    }

    @PostMapping(path = "/conferences")
    public ResponseEntity<ConferenceResponseDto> saveConference(@RequestHeader("Authorization") UUID token, @Valid @RequestBody ConferenceRequestDto conferenceRequestDto) {
        ConferenceResponseDto newConference = conferenceService.saveConference(token, conferenceRequestDto);
        return ResponseEntity.ok(newConference);
    }

    @DeleteMapping(path = "/conferences/{id}")
    public ResponseEntity<Void> deleteConference(@RequestHeader("Authorization") UUID token, @PathVariable("id") Long conferenceId) {
        conferenceService.deleteConference(token, conferenceId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/conferences/{id}")
    public ResponseEntity<ConferenceResponseDto> editConference(@RequestHeader("Authorization") UUID token, @RequestBody ConferencePatchRequestDto conferencePatchRequestDto, @PathVariable("id") Long conferenceId) {
        ConferenceResponseDto conferenceResponseDto = conferenceService.editConference(token, conferenceId, conferencePatchRequestDto);
        return ResponseEntity.ok(conferenceResponseDto);
    }
}