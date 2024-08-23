package com.aug21.springjpavalidation.controller;

import com.aug21.springjpavalidation.dto.EmployeeDTO;
import com.aug21.springjpavalidation.exceptions.EmployeeNotFoundException;
import com.aug21.springjpavalidation.models.Employee;
import com.aug21.springjpavalidation.service.EmployeeService;
import com.opencsv.CSVReader;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/display")
    public String display() {
        return "Hello Employee from Employee Controller ";
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getbyid")
    public Employee getEmployeeById(@RequestParam long id) throws EmployeeNotFoundException {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/update")
    public Employee updateEmployee(@RequestParam long id, @RequestBody Employee employee) throws EmployeeNotFoundException {
        return (employeeService.updateEmployee(id, employee));
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteEmployee(@RequestParam long id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("/getcsv")
    public ResponseEntity<List<Employee>> getCsv(@RequestParam("file") MultipartFile csv) {
        List<Employee> employee_data = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(csv.getInputStream()))) {
            List<String[]> csv_data = reader.readAll().stream().skip(1).toList();

            for (String[] employee : csv_data) {
                Employee emp = Employee.builder()
                        .first_name(employee[1])
                        .last_name(employee[2])
                        .email(employee[3])
                        .department(employee[4])
                        .salary(Double.parseDouble(employee[5]))
                        .build();
                System.out.println(emp);
                employee_data.add(emp);
            }

        } catch (IOException e) {
            System.out.println("Interruption occurred during the execution " + e);
        }
        return employeeService.addall(employee_data);

    }

}
