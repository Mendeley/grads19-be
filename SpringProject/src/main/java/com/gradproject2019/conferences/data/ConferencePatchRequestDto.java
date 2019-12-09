package com.gradproject2019.conferences.data;

import com.gradproject2019.conferences.persistence.Conference;

import java.time.Instant;

import static com.gradproject2019.conferences.persistence.Conference.ConferenceBuilder.aConference;

public class ConferencePatchRequestDto {
    private String name;
    private Instant dateTime;
    private String city;
    private String description;
    private String topic;

    public ConferencePatchRequestDto() {
    }

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

    public static final class ConferencePatchRequestDtoBuilder {
        private String name;
        private Instant dateTime;
        private String city;
        private String description;
        private String topic;

        private ConferencePatchRequestDtoBuilder() {
        }

        public static ConferencePatchRequestDtoBuilder aConferencePatchRequestDto() {
            return new ConferencePatchRequestDtoBuilder();
        }

        public ConferencePatchRequestDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ConferencePatchRequestDtoBuilder withDateTime(Instant dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ConferencePatchRequestDtoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public ConferencePatchRequestDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ConferencePatchRequestDtoBuilder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public ConferencePatchRequestDto build() {
            ConferencePatchRequestDto conferencePatchRequestDto = new ConferencePatchRequestDto();
            conferencePatchRequestDto.city = this.city;
            conferencePatchRequestDto.description = this.description;
            conferencePatchRequestDto.name = this.name;
            conferencePatchRequestDto.dateTime = this.dateTime;
            conferencePatchRequestDto.topic = this.topic;
            return conferencePatchRequestDto;
        }
    }

    public Conference from(Long conferenceId, ConferencePatchRequestDto conferencePatchRequestDto) {
        return aConference()
                .withId(conferenceId)
                .withName(conferencePatchRequestDto.getName())
                .withDateTime(conferencePatchRequestDto.getDateTime())
                .withCity(conferencePatchRequestDto.getCity())
                .withDescription(conferencePatchRequestDto.getDescription())
                .withTopic(conferencePatchRequestDto.getTopic())
                .build();
    }
}