package com.aug21.springjpavalidation.models;

import com.aug21.springjpavalidation.dto.EmployeeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "employee_table")
public class Employee {
    @Id
    @GeneratedValue
    @Column(name = "emp_id")
    private long id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    private String email;
    private String department;
    private double salary;

 public Employee(EmployeeDTO employeeDTO)
 {
     this.first_name = employeeDTO.getFirst_name();
     this.last_name = employeeDTO.getLast_name();
     this.email = employeeDTO.getEmail();
     this.department = employeeDTO.getDepartment();
     this.salary = employeeDTO.getSalary();
 }
}

