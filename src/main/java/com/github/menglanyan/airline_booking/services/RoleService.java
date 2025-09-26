package com.github.menglanyan.airline_booking.services;

import com.github.menglanyan.airline_booking.dtos.Response;
import com.github.menglanyan.airline_booking.dtos.RoleDTO;

import java.util.List;

public interface RoleService {

    Response<?> createRole(RoleDTO roleDTO);

    Response<?> updateRole(RoleDTO roleDTO);

    Response<List<RoleDTO>> getAllRoles();
}
