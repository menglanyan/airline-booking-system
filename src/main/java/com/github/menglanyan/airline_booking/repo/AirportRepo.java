package com.github.menglanyan.airline_booking.repo;

import com.github.menglanyan.airline_booking.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepo extends JpaRepository<Airport, Long> {
    Optional<Airport> findByIataCode(String iataCode);
}
