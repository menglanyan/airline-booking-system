package com.github.menglanyan.airline_booking.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @NotBlank(message = "Recipient is required")
    private String recipientEmail;

    @Lob
    private String body;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private LocalDateTime sentAt;

    private boolean isHtml;
}
