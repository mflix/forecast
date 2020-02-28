package org.mflix.forecast.repository;

import java.util.Set;

import org.mflix.forecast.entity.MovieTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTagRepository extends JpaRepository<MovieTagEntity, Long> {
    Set<MovieTagEntity> findByMovieId(long movieId);
}