package util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import model.Employee;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Component
public class CSVProcessor {

    @Value("classpath:${app.csv.file}")
    private Resource localDataSet;

    private List<Employee> parseEmployees(InputStream inputStream) throws IOException, CsvValidationException {
        List<Employee> employees = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] line;
            boolean isHeader = true;
            while ((line = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

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

    private LocalDate parseDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return LocalDate.parse(date, formatter);
    }
}
