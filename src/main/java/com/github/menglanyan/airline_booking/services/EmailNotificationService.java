package com.github.menglanyan.airline_booking.services;

import com.github.menglanyan.airline_booking.entities.Booking;
import com.github.menglanyan.airline_booking.entities.User;

public interface EmailNotificationService {

    void sendBookingTicketEmail(Booking booking);

    void sendWelcomeEmail(User user);
}
