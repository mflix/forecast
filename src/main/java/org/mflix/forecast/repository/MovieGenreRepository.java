package org.mflix.forecast.repository;

import java.util.Set;

import org.mflix.forecast.entity.MovieGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, Long> {
    Set<MovieGenreEntity> findByMovieId(long movieId);
}