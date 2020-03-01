package org.mflix.forecast.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class LaunchEntity {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String type;
    private Date date;
    private long movieId;

    public LaunchEntity() {
    }

    public LaunchEntity(String type, Date date, long movieId) {
        this.type = type;
        this.date = date;
        this.movieId = movieId;
    }
}