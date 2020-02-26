package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TestEntity {
    @Id
    @GeneratedValue
    private long id;
    private String text;

    public TestEntity() {
    }

    public TestEntity(String text) {
        this.text = text;
    }
}