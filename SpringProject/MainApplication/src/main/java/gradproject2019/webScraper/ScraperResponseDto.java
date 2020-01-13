package gradproject2019.webScraper;

public class ScraperResponseDto {
    private String scrapedConferenceTitle;
    private String scrapedDate;
    private String scrapedTime;
    private String scrapedCity;
    private String scrapedDescription;
    private String scrapedTopic;

    public ScraperResponseDto() {

    }

    public String getScrapedConferenceTitle() {
        return scrapedConferenceTitle;
    }

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

    public String getScrapedTopic() {
        return scrapedTopic;
    }

    public ScraperResponseDto from(ScraperOutput scraperOutput) {
        return new ScraperResponseDtoBuilder()
                .withScrapedConferenceTitle(scraperOutput.getScrapedConferenceTitle())
                .withScrapedDate(scraperOutput.getScrapedConferenceTitle())
                .withScrapedTime(scraperOutput.getScrapedTime())
                .withScrapedCity(scraperOutput.getScrapedCity())
                .withScrapedDescription(scraperOutput.getScrapedDescription())
                .withScrapedTopic(scraperOutput.getScrapedTopic())
                .build();
    }

    public static final class ScraperResponseDtoBuilder {
        private String scrapedConferenceTitle;
        private String scrapedDate;
        private String scrapedTime;
        private String scrapedCity;
        private String scrapedDescription;
        private String scrapedTopic;

        private ScraperResponseDtoBuilder() {
        }

        public static ScraperResponseDtoBuilder ScraperResponseDto() {
            return new ScraperResponseDtoBuilder();
        }

        public ScraperResponseDtoBuilder withScrapedConferenceTitle(String scrapedConferenceTitle) {
            this.scrapedConferenceTitle = scrapedConferenceTitle;
            return this;
        }

        public ScraperResponseDtoBuilder withScrapedDate(String scrapedDate) {
            this.scrapedDate = scrapedDate;
            return this;
        }

        public ScraperResponseDtoBuilder withScrapedTime(String scrapedTime) {
            this.scrapedTime = scrapedTime;
            return this;
        }

        public ScraperResponseDtoBuilder withScrapedCity(String scrapedCity) {
            this.scrapedCity = scrapedCity;
            return this;
        }

        public ScraperResponseDtoBuilder withScrapedDescription(String scrapedDescription) {
            this.scrapedDescription = scrapedDescription;
            return this;
        }
        public ScraperResponseDtoBuilder withScrapedTopic(String scrapedTopic) {
            this.scrapedTopic = scrapedTopic;
            return this;
        }

        public ScraperResponseDto build() {
            ScraperResponseDto scraperResponseDto = new ScraperResponseDto();
            scraperResponseDto.scrapedDescription = this.scrapedDescription;
            scraperResponseDto.scrapedDate = this.scrapedDate;
            scraperResponseDto.scrapedCity = this.scrapedCity;
            scraperResponseDto.scrapedConferenceTitle = this.scrapedConferenceTitle;
            scraperResponseDto.scrapedTime = this.scrapedTime;
            scraperResponseDto.scrapedTopic = this.scrapedTopic;
            return scraperResponseDto;
        }
    }
}
