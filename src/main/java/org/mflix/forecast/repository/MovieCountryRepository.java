package org.mflix.forecast.repository;

import java.util.Set;

import org.mflix.forecast.entity.MovieCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCountryRepository extends JpaRepository<MovieCountryEntity, Long> {
    Set<MovieCountryEntity> findByMovieId(long movieId);
}