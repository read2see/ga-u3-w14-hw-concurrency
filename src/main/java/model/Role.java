package model;

import lombok.Getter;

@Getter
public enum Role {
    DIRECTOR(0.05),
    MANAGER(0.02),
    EMPLOYEE(0.01);

    private final double increment;
    Role(double increment) {
        this.increment = increment;
    }
}
