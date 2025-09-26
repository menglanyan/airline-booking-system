package com.github.menglanyan.airline_booking.entities;

import com.github.menglanyan.airline_booking.enums.City;
import com.github.menglanyan.airline_booking.enums.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "airports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private City city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Country country;

    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;
}
