package com.gradproject2019;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Conference {
    @Id
    private Long id;
    @Column(name = "conference_name")
    private String name;
    @Column(name = "conference_date_time")
    private Instant dateTime;
    private String city;
    @Column(name = "conference_description")
    private String description;
    @Column(name = "conference_topic")
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
