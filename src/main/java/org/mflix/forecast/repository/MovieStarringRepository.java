package org.mflix.forecast.repository;

import java.util.Set;

import org.mflix.forecast.entity.MovieStarringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStarringRepository extends JpaRepository<MovieStarringEntity, Long> {
    Set<MovieStarringEntity> findByMovieId(long movieId);
}