package com.github.menglanyan.airline_booking.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookingRequest {

    @NotNull(message = "Flihgt ID cannot be null")
    private Long flightId;

    @NotEmpty(message = "At least one passenger must be provided")
    private List<PassengerDTO> passengers;
}
