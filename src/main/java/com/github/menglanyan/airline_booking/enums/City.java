package com.github.menglanyan.airline_booking.enums;

import lombok.Getter;

@Getter
public enum City {

    // Cities in UK
    LONDON(Country.UK),
    LEEDS(Country.UK),

    // Cities in CHINA
    BEIJING(Country.CHINA),
    SHANGHAI(Country.CHINA),

    // Cities in USA
    MIAMI(Country.USA),
    DALLAS(Country.USA);

    private final Country country;

    City(Country country) {
        this.country = country;
    }
}
