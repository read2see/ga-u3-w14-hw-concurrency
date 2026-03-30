package com.ga.concurrency.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
/**
 * Represents an employee and the data needed for raise calculations.
 */
public class Employee {

    private String name;
    private BigDecimal currentSalary;
    private BigDecimal afterRaiseSalary;
    private LocalDate joinedDate;
    private Role role;
    private double projectCompletionRate;

    /**
     * Adds an amount directly to the current salary.
     *
     * @param increment amount to add
     */
    public void incrementCurrentSalary(BigDecimal increment) {
        this.currentSalary = this.currentSalary.add(increment);
    }
    /**
     * Computes post-raise salary using the current salary plus increment.
     *
     * @param increment raise amount to apply
     */
    public void incrementSalary(BigDecimal increment) {
        this.afterRaiseSalary = this.currentSalary.add(increment);
    }

    /**
     * Computes post-raise salary by multiplying current salary.
     *
     * @param multiplier multiplier to apply
     */
    public void applySalaryMultiplier(BigDecimal multiplier) {
        this.afterRaiseSalary = this.currentSalary.multiply(multiplier);
    }

    /**
     * Returns full years worked since the joined date.
     *
     * @return elapsed years of service
     */
    public int getElapsedWorkYears() {
        return Period.between(this.joinedDate, LocalDate.now()).getYears();
    }

    /**
     * Checks whether this employee qualifies for a raise.
     *
     * @return true when completion rate is at least 60%
     */
    public boolean isEligibleForRaise() {
        return this.projectCompletionRate >= 0.6;
    }

    /**
     * Checks whether this employee is a high performer.
     *
     * @return true when completion rate is above 80%
     */
    public boolean isHighPerformer() {
        return this.projectCompletionRate > 0.8;
    }
}
