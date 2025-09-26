package com.github.menglanyan.airline_booking.exceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String ex) {
        super(ex);
    }
}
