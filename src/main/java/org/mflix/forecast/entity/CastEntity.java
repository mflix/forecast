package org.mflix.forecast.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CastEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String name;

    public CastEntity() {
    }

    public CastEntity(String name) {
        this.name = name;
    }
}