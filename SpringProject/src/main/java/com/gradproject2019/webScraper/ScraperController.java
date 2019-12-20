package com.gradproject2019.webScraper;

import com.gradproject2019.conferences.data.ConferenceResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    @PostMapping(path = "/add")
    public ResponseEntity<ConferenceResponseDto> getConferenceURL(@RequestHeader("Authorization") UUID token, @RequestBody String conferenceURL) throws Exception {
        validateToken();
        System.out.println(conferenceURL);
        scraperService.startScraper(conferenceURL);
        ConferenceResponseDto responseDto = ConferenceResponseDto.ConferenceResponseDtoBuilder.aConferenceResponseDto().build();

        return ResponseEntity.ok(responseDto);
    }

    //TODO: ensure user is verified before the crawler starts
    private boolean validateToken() {
        return false;
    }
}
