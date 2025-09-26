package com.github.menglanyan.airline_booking.repo;

import com.github.menglanyan.airline_booking.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
