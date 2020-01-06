package com.gradproject2019.webScraper;

import java.time.Instant;

public class ScraperOutput {
    private String scrapedConferenceTitle;
    private Instant scrapedDateTime;
    private String scrapedCity;
    private String scrapedDescription;
    private String scrapedTopic;

    public ScraperOutput(String scrapedConferenceTitle, Instant scrapedDateTime, String scrapedCity, String scrapedDescription, String scrapedTopic) {
        this.scrapedConferenceTitle = scrapedConferenceTitle;
        this.scrapedDateTime = scrapedDateTime;
        this.scrapedCity = scrapedCity;
        this.scrapedDescription = scrapedDescription;
        this.scrapedTopic = scrapedTopic;
    }

    public String getScrapedConferenceTitle() { return scrapedConferenceTitle; }

    public Instant getScrapedDateTime() {
        return scrapedDateTime;
    }

    public String getScrapedCity() {
        return scrapedCity;
    }

    public String getScrapedDescription() {
        return scrapedDescription;
    }

    public String getScrapedTopic() {
        return scrapedTopic;
    }
}
