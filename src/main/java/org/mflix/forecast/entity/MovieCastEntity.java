package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MovieCastEntity {
    @Id
    @GeneratedValue
    private long id;
    private long movieId;
    private long castId;

    public MovieCastEntity() {
    }

    public MovieCastEntity(long movieId, long castId) {
        this.movieId = movieId;
        this.castId = castId;
    }
}