package model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {

    private String name;
    private BigDecimal currentSalary;
    private LocalDate joinedDate;
    private Role role;
    private double projectCompletionRate;

    public void incrementCurrentSalary(BigDecimal increment) {
        this.currentSalary = this.currentSalary.add(increment);
    }

    public void applySalaryMultiplier(BigDecimal multiplier) {
        this.currentSalary = this.currentSalary.multiply(multiplier);
    }

    public int getElapsedWorkYears() {
        return Period.between(this.joinedDate, LocalDate.now()).getYears();
    }

    public boolean isEligibleForRaise() {
        return this.projectCompletionRate >= 0.6;
    }

    public boolean isHighPerformer() {
        return this.projectCompletionRate > 0.8;
    }
}
