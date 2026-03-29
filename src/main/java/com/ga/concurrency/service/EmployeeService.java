package com.ga.concurrency.service;

import com.ga.concurrency.model.Employee;
import com.ga.concurrency.util.CSVProcessor;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmployeeService {
    static final BigDecimal TENURE_INCREMENT_PER_YEAR = new BigDecimal("0.02");
    static final BigDecimal HIGH_PERFORMER_MULTIPLIER = new BigDecimal("1.5");

    private final CSVProcessor csvProcessor;
    private final int poolSize;
    private final int maxConcurrentTasks;

    @Autowired
    public EmployeeService(CSVProcessor csvProcessor) {
        this(csvProcessor, defaultPoolSize(), defaultConcurrencyLimit(defaultPoolSize()));
    }

    public EmployeeService(CSVProcessor csvProcessor, int poolSize, int maxConcurrentTasks) {
        this.csvProcessor = csvProcessor;
        this.poolSize = Math.max(1, poolSize);
        this.maxConcurrentTasks = Math.max(1, maxConcurrentTasks);
    }

    public List<Employee> processEmployees() throws IOException, CsvValidationException {
        return processEmployees(csvProcessor.readEmployees());
    }

    public List<Employee> processEmployees(String filePath) throws IOException, CsvValidationException {
        return processEmployees(csvProcessor.readEmployees(filePath));
    }

    public List<Employee> processEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            return employees;
        }

        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        Semaphore limiter = new Semaphore(maxConcurrentTasks);
        AtomicInteger raisesApplied = new AtomicInteger();

        for (Employee employee : employees) {
            executor.submit(() -> {
                try {
                    limiter.acquire();
                    try {
                        if (applyRaise(employee)) {
                            raisesApplied.incrementAndGet();
                        }
                    } finally {
                        limiter.release();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                }
            });
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return employees;
    }

    boolean applyRaise(Employee employee) {
        if (!employee.isEligibleForRaise()) {
            return false;
        }

        BigDecimal raisePercentage = calculateRaisePercentage(employee);
        BigDecimal raiseAmount = employee.getCurrentSalary()
                .multiply(raisePercentage)
                .setScale(2, RoundingMode.HALF_UP);

        synchronized (employee) {
            employee.incrementSalary(raiseAmount);
        }

        return true;
    }

    BigDecimal calculateRaisePercentage(Employee employee) {
        BigDecimal totalPercentage = employee.getRole().getIncrement().add(calculateTenureIncrease(employee));

        if (employee.isHighPerformer()) {
            totalPercentage = totalPercentage.multiply(HIGH_PERFORMER_MULTIPLIER);
        }

        return totalPercentage;
    }

    private BigDecimal calculateTenureIncrease(Employee employee) {
        int yearsWorked = employee.getElapsedWorkYears();
        if (yearsWorked < 1) {
            return BigDecimal.ZERO;
        }


        return TENURE_INCREMENT_PER_YEAR.multiply(BigDecimal.valueOf(yearsWorked));
    }

    private static int defaultPoolSize() {
        return Math.max(2, Runtime.getRuntime().availableProcessors());
    }

    private static int defaultConcurrencyLimit(int poolSize) {
        return Math.max(2, poolSize/2);
    }
}
