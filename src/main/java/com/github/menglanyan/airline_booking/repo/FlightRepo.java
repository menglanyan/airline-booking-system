package com.github.menglanyan.airline_booking.repo;

import com.github.menglanyan.airline_booking.entities.Flight;
import com.github.menglanyan.airline_booking.entities.User;
import com.github.menglanyan.airline_booking.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepo extends JpaRepository<Flight, Long> {
    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByDepartureAirportIataCodeAndArrivalAirportIataCodeAndStatusAndDepartureTimeBetween(
            String departureIataCode, String arrivalIataCode, FlightStatus status,
            LocalDateTime startOfDay, LocalDateTime endOfDay
    );

    List<Flight> findByAssignedPilotIdOrderByDepartureTimeDesc(Long pilotId);
}
