package com.ga.concurrency.controller;

import com.ga.concurrency.model.Employee;
import com.ga.concurrency.service.EmployeeService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String index(Model model) throws IOException, CsvValidationException {
        List<Employee> employees = employeeService.processEmployees();
        model.addAttribute("employees", employees);
        return "index";
    }
}
