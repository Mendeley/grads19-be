package gradproject2019.webScraper;

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

    public ScraperOutput() {
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

    public String getScrapedTopic() {return  scrapedTopic; }

    public void setScrapedConferenceTitle(String scrapedConferenceTitle) {
        this.scrapedConferenceTitle = scrapedConferenceTitle;
    }

    public void setScrapedDateTime(Instant scrapedDateTime) {
        this.scrapedDateTime = scrapedDateTime;
    }

    public void setScrapedCity(String scrapedCity) {
        this.scrapedCity = scrapedCity;
    }

    public void setScrapedDescription(String scrapedDescription) {
        this.scrapedDescription = scrapedDescription;
    }

    public void setScrapedTopic(String scrapedTopic) { this.scrapedTopic = scrapedTopic; }
}