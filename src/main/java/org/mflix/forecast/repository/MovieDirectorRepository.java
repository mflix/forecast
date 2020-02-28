package org.mflix.forecast.repository;

import java.util.Set;

import org.mflix.forecast.entity.MovieDirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDirectorRepository extends JpaRepository<MovieDirectorEntity, Long> {
    Set<MovieDirectorEntity> findByMovieId(long movieId);
}