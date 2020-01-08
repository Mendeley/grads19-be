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

import static org.hibernate.annotations.common.util.StringHelper.isNotEmpty;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/conferences")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping(path = "/conferences")
    public ResponseEntity<List<ConferenceResponseDto>> getAllConferences(
            //@RequestParam String name,
            //@RequestParam String city,
            //@RequestParam String description,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<ConferenceResponseDto> conferences;

        if (isNotEmpty(topic)) {
            conferences = conferenceService.findByConferenceTopic(topic, page, size);
        } else {
            conferences = conferenceService.getAllConferences();
        }

        return ResponseEntity.ok(conferences);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ConferenceResponseDto> getConferenceById(@PathVariable("id") Long conferenceId) {
        ConferenceResponseDto conferenceResponseDto = conferenceService.getConferenceById(conferenceId);
        return ResponseEntity.ok(conferenceResponseDto);
    }

    @PostMapping
    public ResponseEntity<ConferenceResponseDto> saveConference(@RequestHeader("Authorization") UUID token, @Valid @RequestBody ConferenceRequestDto conferenceRequestDto) {
        ConferenceResponseDto newConference = conferenceService.saveConference(token, conferenceRequestDto);
        return ResponseEntity.ok(newConference);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteConference(@RequestHeader("Authorization") UUID token, @PathVariable("id") Long conferenceId) {
        conferenceService.deleteConference(token, conferenceId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ConferenceResponseDto> editConference(@RequestHeader("Authorization") UUID token, @RequestBody ConferencePatchRequestDto conferencePatchRequestDto, @PathVariable("id") Long conferenceId) {
        ConferenceResponseDto conferenceResponseDto = conferenceService.editConference(token, conferenceId, conferencePatchRequestDto);
        return ResponseEntity.ok(conferenceResponseDto);
    }
}