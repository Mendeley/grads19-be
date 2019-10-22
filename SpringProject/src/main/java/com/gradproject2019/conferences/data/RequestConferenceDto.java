package com.gradproject2019.conferences.data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class RequestConferenceDto {
    private Long id;

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

    public RequestConferenceDto(Long id, String name, Instant dateTime, String city, String description, String topic) {
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

    public static final class RequestConferenceDtoBuilder {
        private Long id;
        private String name;
        private Instant dateTime;
        private String city;
        private String description;
        private String topic;

        private RequestConferenceDtoBuilder() {
        }

        public static RequestConferenceDtoBuilder aConferenceDto() {
            return new RequestConferenceDtoBuilder();
        }

        public RequestConferenceDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public RequestConferenceDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RequestConferenceDtoBuilder withDateTime(Instant dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public RequestConferenceDtoBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public RequestConferenceDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public RequestConferenceDtoBuilder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public RequestConferenceDto build() {
            return new RequestConferenceDto(id, name, dateTime, city, description, topic);
        }
    }
}