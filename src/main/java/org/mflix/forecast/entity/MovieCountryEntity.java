package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MovieCountryEntity {
    @Id
    @GeneratedValue
    private long id;
    private long movieId;
    private long countryId;

    public MovieCountryEntity() {
    }

    public MovieCountryEntity(long movieId, long countryId) {
        this.movieId = movieId;
        this.countryId = countryId;
    }
}