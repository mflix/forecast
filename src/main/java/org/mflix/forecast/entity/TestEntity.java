package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "test")
public class TestEntity {
    @Id
    @GeneratedValue
    private long id;
    private String text;

    // for JPA
    public TestEntity() {
    }

    public TestEntity(String text) {
        this.text = text;
    }
}