package com.github.menglanyan.airline_booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AirlineBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirlineBookingApplication.class, args);
    }

}
