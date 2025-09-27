package com.github.menglanyan.airline_booking.services;

import com.github.menglanyan.airline_booking.dtos.AirportDTO;
import com.github.menglanyan.airline_booking.dtos.Response;

import java.util.List;

public interface AirportService {

    Response<?> createAirport(AirportDTO airportDTO);

    Response<?> updateAirport(AirportDTO airportDTO);

    Response<List<AirportDTO>> getAllAirports();

    Response<AirportDTO> getAirportByID(Long id);
}
