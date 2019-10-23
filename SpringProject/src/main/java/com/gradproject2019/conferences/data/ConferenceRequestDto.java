package com.gradproject2019.conferences.data;

import com.gradproject2019.conferences.persistance.Conference;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Validated
public class ConferenceRequestDto {
    private Long id; // FE should be giving BE id = null

    @NotNull(message = "Invalid entry in conference field.")
    private String name;

    @NotNull(message = "Invalid entry in conference field.")
    private Instant dateTime;

    @NotNull(message = "Invalid entry in conference field.")
    private String city;

    @NotNull(message = "Invalid entry in conference field.")
    private String description;

    @NotNull(message = "Invalid entry in conference field.")
    private String topic;

    public ConferenceRequestDto() { }

    public ConferenceRequestDto(Long id, String name, Instant dateTime, String city, String description, String topic) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.city = city;
        this.description = description;
        this.topic = topic;
    }

    public Long getId() {
        return id;
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

    public Conference from(ConferenceRequestDto conferenceRequestDto) {
        Conference newConference = new Conference(
                    conferenceRequestDto.getId(),
                    conferenceRequestDto.getName(),
                    conferenceRequestDto.getDateTime(),
                    conferenceRequestDto.getCity(),
                    conferenceRequestDto.getDescription(),
                    conferenceRequestDto.getTopic());
        return newConference;
    }

    public static final class ConferenceRequestDtoBuilder {
        private Long id;
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

        public ConferenceRequestDtoBuilder withId(Long id) {
            this.id = id;
            return this;
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
            return new ConferenceRequestDto(id, name, dateTime, city, description, topic);
        }
    }
}