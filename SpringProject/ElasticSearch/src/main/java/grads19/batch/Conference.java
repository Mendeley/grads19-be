package grads19.batch;

public class Conference {
    private String name;
    private String city;
    private String description;
    private String topic;

    public Conference() {
    }

    public Conference(String name, String city, String description, String topic) {
        this.name = name;
        this.city = city;
        this.description = description;
        this.topic = topic;
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
