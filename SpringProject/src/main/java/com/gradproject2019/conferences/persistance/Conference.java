package com.gradproject2019.conferences.persistance;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "conferences")
public class Conference {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private Instant dateTime;
    private String city;
    private String description;
    private String topic;

    public Conference() {
    }

    public Conference(Long id, String name, Instant dateTime, String city, String description, String topic) {
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


    public static final class ConferenceBuilder {
        private Long id;
        private String name;
        private Instant dateTime;
        private String city;
        private String description;
        private String topic;

        private ConferenceBuilder() {
        }

        public static ConferenceBuilder aConference() {
            return new ConferenceBuilder();
        }

        public ConferenceBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ConferenceBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ConferenceBuilder withDateTime(Instant dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ConferenceBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public ConferenceBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ConferenceBuilder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Conference build() {
            return new Conference(id, name, dateTime, city, description, topic);
        }
    }
}