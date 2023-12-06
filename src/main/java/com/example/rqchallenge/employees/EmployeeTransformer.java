package com.example.rqchallenge.employees;

import com.example.rqchallenge.exceptions.ExternalApiException;

public class EmployeeTransformer {

    public static Employee transform(DummyEmployeeDto dummyEmployeeDto) {
        if (dummyEmployeeDto == null) {
            throw new ExternalApiException("Employee data is missing");
        }

        try {
            return new Employee.Builder()
                    .id(Long.parseLong(dummyEmployeeDto.getId()))
                    .employeeName(dummyEmployeeDto.getEmployee_name())
                    .employeeSalary(Integer.parseInt(dummyEmployeeDto.getEmployee_salary()))
                    .employeeAge(Integer.parseInt(dummyEmployeeDto.getEmployee_age()))
                    .profileImage(dummyEmployeeDto.getProfile_image())
                    .build();
        } catch (NumberFormatException e) {
            throw new ExternalApiException("Error parsing employee data: " + e.getMessage());
        }
    }
}
