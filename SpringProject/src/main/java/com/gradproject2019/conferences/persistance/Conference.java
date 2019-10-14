package com.gradproject2019.conferences.persistance;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "conferences")
public class Conference {
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
}
