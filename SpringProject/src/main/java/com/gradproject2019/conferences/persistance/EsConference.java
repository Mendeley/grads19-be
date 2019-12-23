package com.gradproject2019.conferences.persistance;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.Instant;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

@Document(indexName = "conferences", type = "conference")
public class EsConference {
    @Id
    private Long id;
    private String name;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZZ", timezone = "UTC")
    //private Instant dateTime;

    private String city;
    private String description;
    private String topic;

    public EsConference() {
    }

    public EsConference(Long id, String name, Instant dateTime, String city, String description, String topic) {
        this.id = id;
        this.name = name;
        //this.dateTime = dateTime;
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

    //public Instant getDateTime() {
        //return dateTime;
    //}

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getTopic() {
        return topic;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setDateTime(Instant dateTime) {
//        this.dateTime = dateTime;
//    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}


