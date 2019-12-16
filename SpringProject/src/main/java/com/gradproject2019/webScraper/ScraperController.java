package com.gradproject2019.webScraper;

import com.gradproject2019.conferences.data.ConferenceResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

//@Controller
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    @PostMapping(path = "/conferences")
    public ResponseEntity<ConferenceResponseDto> getConferenceURL(@RequestHeader("Authorization") UUID token, @RequestBody String conferenceURL) {
        ScraperURL newScraperURL = scraperService.getConferenceURL(token, conferenceURL);
        return ResponseEntity.ok(ConferenceResponseDto);
    }
}
