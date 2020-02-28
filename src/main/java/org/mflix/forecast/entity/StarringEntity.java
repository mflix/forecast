package org.mflix.forecast.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class StarringEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String name;

    public StarringEntity() {
    }

    public StarringEntity(String name) {
        this.name = name;
    }
}