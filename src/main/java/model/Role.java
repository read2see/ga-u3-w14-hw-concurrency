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
}
