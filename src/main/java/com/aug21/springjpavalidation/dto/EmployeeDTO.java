package com.aug21.springjpavalidation.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmployeeDTO {


    @NotEmpty(message = "First name should not be empty")
    private String first_name;

    @NotEmpty(message = "Last name should not be empty")
    private String last_name;

    @NotBlank(message = "Email should not be left empty")
    @Pattern(regexp = "",message = "email is invalid refer -->(ex :meka123@gmail.com)")
    private String email;

    @NotEmpty(message = "Dept should not be empty")
    private String department;

    @Min(value = 12500, message = "Salary should not be less than 12500")
    @Max(value = 50000, message = "Salary should be less then 50000")
    private double salary;


}
