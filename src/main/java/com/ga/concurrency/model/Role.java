package com.ga.concurrency.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
/**
 * Employee roles and their base raise increments.
 */
public enum Role {
    DIRECTOR(new BigDecimal("0.05")),
    MANAGER(new BigDecimal("0.02")),
    EMPLOYEE(new BigDecimal("0.01"));

    private final BigDecimal increment;
    Role(BigDecimal increment) {
        this.increment = increment;
    }

    /**
     * Parses a role value from text.
     *
     * @param value role name
     * @return matching {@link Role}
     * @throws IllegalArgumentException when the value does not match a role
     */
    public static Role fromString(String value) {
        return switch(value.trim().toUpperCase()){
            case "DIRECTOR" -> DIRECTOR;
            case "MANAGER" -> MANAGER;
            case "EMPLOYEE" -> EMPLOYEE;
            default -> throw new IllegalArgumentException("Invalid Role: " + value);
        };
    }
}
