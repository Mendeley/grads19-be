package com.gradproject2019.webScraper;

import java.util.UUID;

public interface ScraperService {

    ScraperURL getConferenceURL(UUID token, String conferenceURL);

}
