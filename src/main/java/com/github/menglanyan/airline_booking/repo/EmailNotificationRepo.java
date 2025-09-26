package com.github.menglanyan.airline_booking.repo;

import com.github.menglanyan.airline_booking.entities.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailNotificationRepo extends JpaRepository<EmailNotification, Long> {
}
