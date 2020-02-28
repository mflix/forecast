package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MovieDirectorEntity {
    @Id
    @GeneratedValue
    private long id;
    private long movieId;
    private long directorId;

    public MovieDirectorEntity() {
    }

    public MovieDirectorEntity(long movieId, long directorId) {
        this.movieId = movieId;
        this.directorId = directorId;
    }
}