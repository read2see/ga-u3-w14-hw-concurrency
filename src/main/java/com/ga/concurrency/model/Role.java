package model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Role {
    DIRECTOR(new BigDecimal("0.05")),
    MANAGER(new BigDecimal("0.02")),
    EMPLOYEE(new BigDecimal("0.01"));

    private final BigDecimal increment;
    Role(BigDecimal increment) {
        this.increment = increment;
    }

    public static Role fromString(String value) {
        return switch(value.trim().toUpperCase()){
            case "DIRECTOR" -> DIRECTOR;
            case "MANAGER" -> MANAGER;
            case "EMPLOYEE" -> EMPLOYEE;
            default -> throw new IllegalArgumentException("Invalid Role: " + value);
        };
    }
}
