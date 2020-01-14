package gradproject2019.webScraper;

import java.time.Instant;

public class ScraperResponseDto {
    private String scrapedConferenceTitle;
    private Instant scrapedDateTime;
    private String scrapedCity;
    private String scrapedDescription;
    private String scrapedTopic;

    public ScraperResponseDto() {

    }

    public String getScrapedConferenceTitle() {
        return scrapedConferenceTitle;
    }

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

    public ScraperResponseDto from(ScraperOutput scraperOutput) {
        return new ScraperResponseDtoBuilder()
                .withScrapedConferenceTitle(scraperOutput.getScrapedConferenceTitle())
                .withScrapedDateTime(scraperOutput.getScrapedDateTime())
                .withScrapedCity(scraperOutput.getScrapedCity())
                .withScrapedDescription(scraperOutput.getScrapedDescription())
                .withScrapedTopic(scraperOutput.getScrapedTopic())
                .build();
    }

    public static final class ScraperResponseDtoBuilder {
        private String scrapedConferenceTitle;
        private Instant scrapedDateTime;
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

        public ScraperResponseDtoBuilder withScrapedDateTime(Instant scrapedDateTime) {
            this.scrapedDateTime = scrapedDateTime;
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
            scraperResponseDto.scrapedDateTime = this.scrapedDateTime;
            scraperResponseDto.scrapedCity = this.scrapedCity;
            scraperResponseDto.scrapedConferenceTitle = this.scrapedConferenceTitle;
            scraperResponseDto.scrapedTopic = this.scrapedTopic;
            return scraperResponseDto;
        }
    }
}
