package gradproject2019.webScraper;

public class ScraperOutput {
    private String scrapedConferenceTitle;
    private String scrapedDate;
    private String scrapedTime;
    private String scrapedCity;
    private String scrapedDescription;

    public ScraperOutput(String scrapedConferenceTitle, String scrapedDate, String scrapedTime, String scrapedCity, String scrapedDescription) {
        this.scrapedConferenceTitle = scrapedConferenceTitle;
        this.scrapedDate = scrapedDate;
        this.scrapedTime = scrapedTime;
        this.scrapedCity = scrapedCity;
        this.scrapedDescription = scrapedDescription;
    }

    public ScraperOutput() {
    }

    public String getScrapedConferenceTitle() { return scrapedConferenceTitle; }

    public String getScrapedDate() {
        return scrapedDate;
    }

    public String getScrapedTime() {
        return scrapedTime;
    }

    public String getScrapedCity() {
        return scrapedCity;
    }

    public String getScrapedDescription() {
        return scrapedDescription;
    }

    public void setScrapedConferenceTitle(String scrapedConferenceTitle) {
        this.scrapedConferenceTitle = scrapedConferenceTitle;
    }

    public void setScrapedDate(String scrapedDate) {
        this.scrapedDate = scrapedDate;
    }

    public void setScrapedTime(String scrapedTime) {
        this.scrapedTime = scrapedTime;
    }

    public void setScrapedCity(String scrapedCity) {
        this.scrapedCity = scrapedCity;
    }

    public void setScrapedDescription(String scrapedDescription) {
        this.scrapedDescription = scrapedDescription;
    }

}
