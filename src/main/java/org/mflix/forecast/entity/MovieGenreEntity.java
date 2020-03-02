package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MovieGenreEntity {
    @Id
    @GeneratedValue
    private long id;
    private long movieId;
    private long genreId;

    public MovieGenreEntity() {
    }

    public MovieGenreEntity(long movieId, long genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }
}