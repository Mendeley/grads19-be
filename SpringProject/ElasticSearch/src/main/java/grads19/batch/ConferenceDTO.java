package grads19.batch;

public class ConferenceDTO {

    private String name;
    private String city;
    private String description;
    private String topic;

    public ConferenceDTO() {
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

