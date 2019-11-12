package com.gradproject2019.conferences.data;

import com.gradproject2019.conferences.persistance.Conference;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Instant;

import static com.gradproject2019.conferences.persistance.Conference.ConferenceBuilder.aConference;

@Validated
public class ConferenceRequestDto {
    @NotNull(message = "Invalid entry in conference name field.")
    private String name;

    @NotNull(message = "Invalid entry in conference date time field.")
    private Instant dateTime;

    @NotNull(message = "Invalid entry in conference city field.")
    private String city;

    @NotNull(message = "Invalid entry in conference description field.")
    private String description;

    @NotNull(message = "Invalid entry in conference topic field.")
    private String topic;

    public ConferenceRequestDto() { }

    public String getName() {
        return name;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getTopic() {
        return topic;
    }

    public static Conference from(ConferenceRequestDto conferenceRequestDto) {
        return aConference()
                    .withName(conferenceRequestDto.getName())
                    .withDateTime(conferenceRequestDto.getDateTime())
                    .withCity(conferenceRequestDto.getCity())
                    .withDescription(conferenceRequestDto.getDescription())
                    .withTopic(conferenceRequestDto.getTopic())
                    .build();
    }

    public static final class ConferenceRequestDtoBuilder {
        private String name;
        private Instant dateTime;
        private String city;
        private String description;
        private String topic;

        private ConferenceRequestDtoBuilder() {
        }

        public static ConferenceRequestDtoBuilder aConferenceRequestDto() {
            return new ConferenceRequestDtoBuilder();
        }

        public ConferenceRequestDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ConferenceRequestDtoBuilder withDateTime(Instant dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ConferenceRequestDtoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public ConferenceRequestDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ConferenceRequestDtoBuilder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public ConferenceRequestDto build() {
            ConferenceRequestDto conferenceRequestDto = new ConferenceRequestDto();
            conferenceRequestDto.description = this.description;
            conferenceRequestDto.city = this.city;
            conferenceRequestDto.topic = this.topic;
            conferenceRequestDto.name = this.name;
            conferenceRequestDto.dateTime = this.dateTime;
            return conferenceRequestDto;
        }
    }
}