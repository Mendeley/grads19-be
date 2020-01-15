package grads19.batch;

import java.sql.Timestamp;
import java.time.Instant;

public class ConferenceDTO {

    private Long id;
    private Instant dateTime;
    private String name;
    private String city;
    private String description;
    private String topic;

    public ConferenceDTO() {
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


    public static final class ConferenceDTOBuilder {
        private ConferenceDTO conferenceDTO;

        private ConferenceDTOBuilder() {
            conferenceDTO = new ConferenceDTO();
        }

        public static ConferenceDTOBuilder aConferenceDTO() {
            return new ConferenceDTOBuilder();
        }

        public ConferenceDTOBuilder withId(Long id) {
            conferenceDTO.setId(id);
            return this;
        }

        public ConferenceDTOBuilder withDateTime(Instant dateTime) {
            conferenceDTO.setDateTime(dateTime);
            return this;
        }

        public ConferenceDTOBuilder withName(String name) {
            conferenceDTO.setName(name);
            return this;
        }

        public ConferenceDTOBuilder withCity(String city) {
            conferenceDTO.setCity(city);
            return this;
        }

        public ConferenceDTOBuilder withDescription(String description) {
            conferenceDTO.setDescription(description);
            return this;
        }

        public ConferenceDTOBuilder withTopic(String topic) {
            conferenceDTO.setTopic(topic);
            return this;
        }

        public ConferenceDTO build() {
            return conferenceDTO;
        }
    }

}

