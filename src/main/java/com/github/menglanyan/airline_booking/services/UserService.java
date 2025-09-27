package com.github.menglanyan.airline_booking.services;

import com.github.menglanyan.airline_booking.dtos.Response;
import com.github.menglanyan.airline_booking.dtos.UserDTO;
import com.github.menglanyan.airline_booking.entities.User;

import java.util.List;

public interface UserService {

    User currentUser();

    Response<?> updateMyAccount(UserDTO userDTO);

    Response<List<UserDTO>> getAllPilots();

    Response<UserDTO> getAccountDetails();
}
