package org.mflix.forecast.repository;

import java.util.Optional;

import org.mflix.forecast.entity.CastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepository extends JpaRepository<CastEntity, Long> {
    Optional<CastEntity> findByName(String name);
}