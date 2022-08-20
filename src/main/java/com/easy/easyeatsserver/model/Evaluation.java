package com.easy.easyeatsserver.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "evaluation")
public class Evaluation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Field(type = FieldType.Integer)
    private Integer id;

    private String message;
    private String restaurant;

    public Evaluation(Integer id, String message, String restaurant) {
        this.id = id;
        this.message = message;
        this.restaurant = restaurant;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getRestaurant() {
        return restaurant;
    }
}
