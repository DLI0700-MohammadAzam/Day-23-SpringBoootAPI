package com.aug21.springjpavalidation.service;

import com.aug21.springjpavalidation.dto.EmployeeDTO;
import com.aug21.springjpavalidation.exceptions.EmployeeNotFoundException;
import com.aug21.springjpavalidation.models.Employee;
import com.aug21.springjpavalidation.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        log.info("---------------- New record created ----------------");
        Employee employee = new Employee(employeeDTO);

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        log.info("---------------- All the employees are retried ----------------");
        return employeeRepository.findAll();
    }


    public Employee getEmployeeById(long id) throws EmployeeNotFoundException {
        log.info("---------------- getting employee by id ----------------");
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("No employee found with that ID "));

    }

    public Employee updateEmployee(long id, Employee employee) throws EmployeeNotFoundException {
        log.info("---------------- Updating employee by Id ----------------");

        Employee u_employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("No employee found with that ID"));
        u_employee.setFirst_name(employee.getFirst_name());
        u_employee.setLast_name(employee.getLast_name());
        u_employee.setEmail(employee.getEmail());
        u_employee.setDepartment(employee.getDepartment());
        u_employee.setSalary(employee.getSalary());
        employeeRepository.save(u_employee);
        return u_employee;


    }


    public ResponseEntity<String> deleteEmployee(long id) {
        Employee delete;
        try {
            delete = getEmployeeById(id);
        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (delete != null) {
            employeeRepository.deleteById(id);
            log.info("---------------- Deleting employee by Id ----------------");
            return new ResponseEntity<>("Deleted", HttpStatus.FOUND);
        }

        return new ResponseEntity<>("Not found ",HttpStatus.BAD_REQUEST);

    }

    @Transactional
    public ResponseEntity<List<Employee>> addall(List<Employee> employeeData) {
        log.info("----------------- Added all the data into the data base using CSV file -------------");
        employeeRepository.saveAll(employeeData);
        return new ResponseEntity<>(employeeData, HttpStatus.ACCEPTED);
    }
}
