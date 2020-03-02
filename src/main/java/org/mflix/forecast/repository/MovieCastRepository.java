package org.mflix.forecast.repository;

import java.util.Set;

import org.mflix.forecast.entity.MovieCastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCastRepository extends JpaRepository<MovieCastEntity, Long> {
    Set<MovieCastEntity> findByMovieId(long movieId);
}