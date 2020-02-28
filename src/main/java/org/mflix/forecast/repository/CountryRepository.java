package org.mflix.forecast.repository;

import java.util.Optional;

import org.mflix.forecast.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    Optional<CountryEntity> findByName(String name);
}