package grads19.batch;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

public class Conference implements Serializable {
    private Long id;
    private String name;
    private String city;
    private String description;
    private String topic;
    private Instant dateTime;

    public Conference() {
    }

    public Conference(Long id, String name, String city, String description, String topic, Instant dateTime ) {
        this.id = id;
        this.dateTime = dateTime;
        this.name = name;
        this.city = city;
        this.description = description;
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public static final class ConferenceBuilder {
        private Conference conference;

        private ConferenceBuilder() {
            conference = new Conference();
        }

        public static ConferenceBuilder aConference() {
            return new ConferenceBuilder();
        }

        public ConferenceBuilder withName(String name) {
            conference.setName(name);
            return this;
        }

        public ConferenceBuilder withDateTime(Instant dateTime) {
            conference.setDateTime(dateTime);
            return this;
        }

        public ConferenceBuilder withId(Long id) {
            conference.setId(id);
            return this;
        }

        public ConferenceBuilder withCity(String city) {
            conference.setCity(city);
            return this;
        }

        public ConferenceBuilder withDescription(String description) {
            conference.setDescription(description);
            return this;
        }

        public ConferenceBuilder withTopic(String topic) {
            conference.setTopic(topic);
            return this;
        }

        public Conference build() {
            return conference;
        }
    }
}