package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MovieTagEntity {
    @Id
    @GeneratedValue
    private long id;
    private long movieId;
    private long tagId;

    public MovieTagEntity() {
    }

    public MovieTagEntity(long movieId, long tagId) {
        this.movieId = movieId;
        this.tagId = tagId;
    }
}