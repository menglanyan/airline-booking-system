package com.github.menglanyan.airline_booking.services;

import com.github.menglanyan.airline_booking.dtos.LoginRequest;
import com.github.menglanyan.airline_booking.dtos.LoginResponse;
import com.github.menglanyan.airline_booking.dtos.RegistrationRequest;
import com.github.menglanyan.airline_booking.dtos.Response;

public interface AuthService {

    Response<?> register(RegistrationRequest registrationRequest);

    Response<LoginResponse> login(LoginRequest loginRequest);
}
