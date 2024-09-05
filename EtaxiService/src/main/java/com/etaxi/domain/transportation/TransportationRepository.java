package com.etaxi.domain.transportation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Integer> {
    Optional<Transportation> findByTitle(String title);
}
