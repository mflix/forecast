package org.mflix.forecast.repository;

import java.util.Optional;

import org.mflix.forecast.entity.StarringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarringRepository extends JpaRepository<StarringEntity, Long> {
    Optional<StarringEntity> findByName(String name);
}