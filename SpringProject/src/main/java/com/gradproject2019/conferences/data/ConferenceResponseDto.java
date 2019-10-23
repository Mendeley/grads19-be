package com.gradproject2019.conferences.data;

import com.gradproject2019.conferences.persistance.Conference;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Validated
public class ConferenceResponseDto {
    @NotNull
    private Long id; // BE should be giving FE id!=null

    @NotNull
    private String name;

    @NotNull
    private Instant dateTime;

    @NotNull
    private String city;

    @NotNull
    private String description;

    @NotNull
    private String topic;

    public ConferenceResponseDto() {
    }

    public ConferenceResponseDto(Long id, String name, Instant dateTime, String city, String description, String topic) {
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

    public ConferenceResponseDto from(Conference conference) {
        ConferenceResponseDto newDto = new ConferenceResponseDtoBuilder()
                .withId(conference.getId())
                .withName(conference.getName())
                .withDateTime(conference.getDateTime())
                .withCity(conference.getCity())
                .withDescription(conference.getDescription())
                .withTopic(conference.getTopic())
                .build();
        return newDto;
    }

    public static final class ConferenceResponseDtoBuilder {
        private Long id;
        private String name;
        private Instant dateTime;
        private String city;
        private String description;
        private String topic;

        private ConferenceResponseDtoBuilder() {
        }

        public static ConferenceResponseDtoBuilder aConferenceResponseDto() {
            return new ConferenceResponseDtoBuilder();
        }

        public ConferenceResponseDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ConferenceResponseDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ConferenceResponseDtoBuilder withDateTime(Instant dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ConferenceResponseDtoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public ConferenceResponseDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ConferenceResponseDtoBuilder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public ConferenceResponseDto build() {
            return new ConferenceResponseDto(id, name, dateTime, city, description, topic);
        }
    }
}