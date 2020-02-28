package org.mflix.forecast.repository;

import java.util.Set;

import org.mflix.forecast.entity.LaunchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaunchRepository extends JpaRepository<LaunchEntity, Long> {
    Set<LaunchEntity> findByMovieId(long movieId);
}