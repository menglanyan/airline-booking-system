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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock private UserRepo userRepo;
    @Mock private RoleRepo roleRepo;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtUtils jwtUtils;
    @Mock private EmailNotificationServiceImpl emailNotificationService;

    @InjectMocks AuthServiceImpl authService;

    @Test
    void register_shouldAddDefaultCustomerRole_evenWhenOtherRolesProvided() {

        RegistrationRequest req = new RegistrationRequest();
        req.setName("Alice");
        req.setEmail("a@example.com");
        req.setPhoneNumber("123");
        req.setPassword("password");
        req.setRoles(List.of("ADMIN", "PILOT"));

        when(userRepo.existsByEmail("a@example.com")).thenReturn(false);

        Role customer = new Role();
        customer.setName("CUSTOMER");
        Role admin = new Role();
        admin.setName("ADMIN");
        Role pilot = new Role();
        pilot.setName("PILOT");

        when(roleRepo.findByName("CUSTOMER")).thenReturn(Optional.of(customer));
        when(roleRepo.findByName("ADMIN")).thenReturn(Optional.of(admin));
        when(roleRepo.findByName("PILOT")).thenReturn(Optional.of(pilot));

        when(passwordEncoder.encode("password")).thenReturn("ENC");
        when(userRepo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        Response<?> resp = authService.register(req);

        assertEquals(HttpStatus.OK.value(), resp.getStatusCode());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(captor.capture());
        User saved = captor.getValue();

        assertEquals("Alice", saved.getName());
        assertEquals("a@example.com", saved.getEmail());
        assertEquals("ENC", saved.getPassword());
        assertEquals(AuthMethod.LOCAL, saved.getProvider());
        assertTrue(saved.isActive());

        Set<String> roleNames = new HashSet<>();
        saved.getRoles().forEach(r -> roleNames.add(r.getName()));
        assertTrue(roleNames.containsAll(List.of("CUSTOMER","ADMIN","PILOT")));

        verify(emailNotificationService).sendWelcomeEmail(saved);
    }

    @Test
    void register_shouldThrow_whenEmailExists() {
        RegistrationRequest req = new RegistrationRequest();
        req.setEmail("dup@example.com");

        when(userRepo.existsByEmail("dup@example.com")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> authService.register(req));
        verifyNoMoreInteractions(roleRepo, passwordEncoder, userRepo);
    }

    @Test
    void login_success_returnsTokenAndRoles() {
        LoginRequest req = new LoginRequest();
        req.setEmail("a@example.com");
        req.setPassword("raw");

        User u = new User();
        u.setEmail("a@example.com");
        u.setPassword("ENC");
        u.setActive(true);
        Role r = new Role(); r.setName("CUSTOMER");
        u.setRoles(List.of(r));
        u.setCreatedAt(LocalDateTime.now());
        u.setUpdatedAt(LocalDateTime.now());

        when(userRepo.findByEmail("a@example.com")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("raw", "ENC")).thenReturn(true);
        when(jwtUtils.generateToken("a@example.com")).thenReturn("JWT");

        Response<LoginResponse> resp = authService.login(req);

        assertEquals(HttpStatus.OK.value(), resp.getStatusCode());
        assertEquals("Login Successful", resp.getMessage());
        assertEquals("JWT", resp.getData().getToken());
        assertEquals(List.of("CUSTOMER"), resp.getData().getRoles());
    }

    @Test
    void login_shouldFail_whenInactive() {
        LoginRequest req = new LoginRequest();
        req.setEmail("a@example.com");
        req.setPassword("raw");

        User u = new User();
        u.setEmail("a@example.com");
        u.setPassword("ENC");
        u.setActive(false);

        when(userRepo.findByEmail("a@example.com")).thenReturn(Optional.of(u));

        assertThrows(NotFoundException.class, () -> authService.login(req));
    }

    @Test
    void login_shouldFail_whenPasswordNotMatch() {
        LoginRequest req = new LoginRequest();
        req.setEmail("a@example.com");
        req.setPassword("raw");

        User u = new User();
        u.setEmail("a@example.com");
        u.setPassword("ENC");
        u.setActive(true);

        when(userRepo.findByEmail("a@example.com")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("raw", "ENC")).thenReturn(false);

        assertThrows(BadRequestException.class, () -> authService.login(req));
    }
}
