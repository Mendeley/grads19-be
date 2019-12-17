package com.gradproject2019.conferences.persistance;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;
import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "conferences_index", type = "conference")
public class ESConference {

    @Id
    private String id;

    @Field(type = Text)
    private String name;

    @Field(type = Keyword)
    private String city;

    @Field(type = Text)
    private String description;

    @Field(type = Keyword)
    private String topic;

    public ESConference() {
    }

    public ESConference(String id, String name, String city, String description, String topic) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.description = description;
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

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


