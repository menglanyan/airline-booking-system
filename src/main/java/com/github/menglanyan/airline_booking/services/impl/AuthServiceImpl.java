package com.github.menglanyan.airline_booking.services.impl;

import com.github.menglanyan.airline_booking.dtos.LoginRequest;
import com.github.menglanyan.airline_booking.dtos.LoginResponse;
import com.github.menglanyan.airline_booking.dtos.RegistrationRequest;
import com.github.menglanyan.airline_booking.dtos.Response;
import com.github.menglanyan.airline_booking.entities.Role;
import com.github.menglanyan.airline_booking.entities.User;
import com.github.menglanyan.airline_booking.enums.AuthMethod;
import com.github.menglanyan.airline_booking.exceptions.BadRequestException;
import com.github.menglanyan.airline_booking.exceptions.NotFoundException;
import com.github.menglanyan.airline_booking.repo.RoleRepo;
import com.github.menglanyan.airline_booking.repo.UserRepo;
import com.github.menglanyan.airline_booking.security.JwtUtils;
import com.github.menglanyan.airline_booking.services.AuthService;
import com.github.menglanyan.airline_booking.services.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final RoleRepo roleRepo;

    private final EmailNotificationService emailNotificationService;

    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {
        log.info("Inside register()");

        // Check if email already exist
        if (userRepo.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("Email already exists.");
        }

        // Prepare roles for new user, user will always have a default role of CUSTOMER
        Role defaultRole = roleRepo.findByName("CUSTOMER")
                .orElseThrow(() -> new NotFoundException("Role CUSTOMER does not exist."));

        List<Role> userRoles;
        if (registrationRequest.getRoles() != null && !registrationRequest.getRoles().isEmpty()) {
            userRoles = new ArrayList<>(
                    registrationRequest.getRoles().stream()
                    .map(roleName -> roleRepo.findByName(roleName.toUpperCase())
                            .orElseThrow(() -> new NotFoundException("Role" + roleName + "Not Found")))
                    .toList()
            );
            userRoles.add(defaultRole);
        } else {
            userRoles = List.of(defaultRole);
        }

        User userToSave = new User();
        userToSave.setName(registrationRequest.getName());
        userToSave.setEmail(registrationRequest.getEmail());
        userToSave.setPhoneNumber(registrationRequest.getPhoneNumber());
        userToSave.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToSave.setRoles(userRoles);
        userToSave.setCreatedAt(LocalDateTime.now());
        userToSave.setUpdatedAt(LocalDateTime.now());
        userToSave.setProvider(AuthMethod.LOCAL);
        userToSave.setActive(true);

        User savedUser = userRepo.save(userToSave);

        emailNotificationService.sendWelcomeEmail(savedUser);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User registered successfully.")
                .build();
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {
        log.info("Inside login()");

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email Not Found"));

        if (!user.isActive()) {
            throw new NotFoundException("Account is not active. Please reach out the user");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid Password");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roleNames);

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Successful")
                .data(loginResponse)
                .build();
    }
}
