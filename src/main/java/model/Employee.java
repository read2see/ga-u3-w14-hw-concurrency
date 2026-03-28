package model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Employee {

    private String name;
    private BigDecimal currentSalary;
    private LocalDate joinedDate;
    private Role role;
    private double projectCompletionRate;
}
