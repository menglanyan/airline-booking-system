package com.github.menglanyan.airline_booking.repo;

import com.github.menglanyan.airline_booking.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r where r.name = :roleName")
    List<User> findByRoleName(@Param("roleName") String roleName);
}
