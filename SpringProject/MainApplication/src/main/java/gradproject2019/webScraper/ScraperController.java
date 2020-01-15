package gradproject2019.webScraper;

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
    public ResponseEntity<ScraperResponseDto> getConferenceURL(@RequestHeader("Authorization") UUID token, @RequestBody String conferenceURL) throws Exception {
        ScraperResponseDto scraperResponseDto = scraperService.startScraper(conferenceURL);

        return ResponseEntity.ok(scraperResponseDto);
    }

    //TODO: ensure user is verified before the crawler starts
}