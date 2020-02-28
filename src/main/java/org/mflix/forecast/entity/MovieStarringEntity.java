package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MovieStarringEntity {
    @Id
    @GeneratedValue
    private long id;
    private long movieId;
    private long starringId;

    public MovieStarringEntity() {
    }

    public MovieStarringEntity(long movieId, long starringId) {
        this.movieId = movieId;
        this.starringId = starringId;
    }
}