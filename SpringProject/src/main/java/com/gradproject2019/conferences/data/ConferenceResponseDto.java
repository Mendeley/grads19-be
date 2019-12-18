package com.gradproject2019.conferences.data;

import com.gradproject2019.conferences.persistance.Conference;
import com.gradproject2019.conferences.persistance.EsConference;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Validated
public class ConferenceResponseDto {
    @NotNull
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

    public ConferenceResponseDto() {
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
        return new ConferenceResponseDtoBuilder()
                .withId(conference.getId())
                .withName(conference.getName())
                .withDateTime(conference.getDateTime())
                .withCity(conference.getCity())
                .withDescription(conference.getDescription())
                .withTopic(conference.getTopic())
                .build();
    }

    public ConferenceResponseDto from(EsConference esConference) {
        return new ConferenceResponseDtoBuilder()
                .withId(esConference.getId())
                .withName(esConference.getName())
//                .withDateTime(esConference.getDateTime()) //FIXME: add to EsConference
                .withCity(esConference.getCity())
                .withDescription(esConference.getDescription())
                .withTopic(esConference.getTopic())
                .build();
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
            ConferenceResponseDto conferenceResponseDto = new ConferenceResponseDto();
            conferenceResponseDto.city = this.city;
            conferenceResponseDto.name = this.name;
            conferenceResponseDto.dateTime = this.dateTime;
            conferenceResponseDto.id = this.id;
            conferenceResponseDto.description = this.description;
            conferenceResponseDto.topic = this.topic;
            return conferenceResponseDto;
        }
    }
}