package com.ga.concurrency.util;

import com.ga.concurrency.model.Employee;
import com.ga.concurrency.model.Role;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVProcessor {
    private static final DateTimeFormatter US_DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");

    @Value("classpath:${app.csv.file}")
    private Resource localDataSet;

    public List<Employee> readEmployees() throws IOException, CsvValidationException {
        try (InputStream inputStream = localDataSet.getInputStream()) {
            return parseEmployees(inputStream);
        }
    }

    public List<Employee> readEmployees(String filePath) throws IOException, CsvValidationException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
        }

        try (InputStream is = inputStream) {
            return parseEmployees(is);
        }
    }

    private List<Employee> parseEmployees(InputStream inputStream) throws IOException, CsvValidationException {
        List<Employee> employees = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] line;
            while ((line = reader.readNext()) != null) {

                Employee employee = new Employee();
                employee.setName(line[1]);
                employee.setCurrentSalary(new BigDecimal(line[2]));
                employee.setJoinedDate(parseDate(line[3]));
                employee.setRole(Role.fromString(line[4]));
                employee.setProjectCompletionRate(Double.parseDouble(line[5]));
                employees.add(employee);
            }
        }

        return employees;
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException ignored) {
            return LocalDate.parse(date, US_DATE_FORMATTER);
        }
    }
}
