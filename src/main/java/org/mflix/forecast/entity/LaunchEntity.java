package org.mflix.forecast.entity;

import java.util.Date;

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
    private String version;
    private Date date;
    private long movieId;

    public LaunchEntity() {
    }

    public LaunchEntity(String version, Date date, long movieId) {
        this.version = version;
        this.date = date;
        this.movieId = movieId;
    }
}