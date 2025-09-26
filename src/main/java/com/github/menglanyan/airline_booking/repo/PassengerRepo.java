package com.github.menglanyan.airline_booking.repo;

import com.github.menglanyan.airline_booking.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepo extends JpaRepository<Passenger, Long> {
}
