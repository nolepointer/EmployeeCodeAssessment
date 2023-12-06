package com.example.rqchallenge.employees;

import org.springframework.lang.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MockEmployeeApiService implements EmployeeApiService {

    @Override
    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<>();
        String[] playerNames = { "Tom Brady", "Mike Evans", "Chris Godwin", "Rob Gronkowski", "Leonard Fournette",
                "Devin White", "Lavonte David", "Shaquil Barrett", "Vita Vea", "Antoine Winfield Jr.", "Ryan Succop" };
        Random random = new Random();

        for (int i = 0; i < playerNames.length; i++) {
            employees.add(new Employee.Builder()
                    .id(i + 1)
                    .employeeName(playerNames[i])
                    .employeeSalary(50000 + random.nextInt(50000)) // Random salary between 50000 and 100000
                    .employeeAge(25 + random.nextInt(15)) // Random age between 25 and 40
                    .profileImage("image" + (i + 1) + ".jpg")
                    .build());
        }

        return employees;
    }

    @Override
    public Employee getEmployeeById(String id) {

        return new Employee.Builder()
                .id(Long.parseLong(id))
                .employeeName("John Doe")
                .employeeSalary(50000)
                .employeeAge(30)
                .profileImage("image1.jpg")
                .build();
    }

    @Override
    public Employee createEmployee(@NonNull Map<String, Object> employeeInput) {

        return new Employee.Builder()
                .id(new Random().nextLong())
                .employeeName(employeeInput.get("name").toString())
                .employeeSalary(Integer.parseInt(employeeInput.get("salary").toString()))
                .employeeAge(Integer.parseInt(employeeInput.get("age").toString()))
                .build();
    }

    @Override
    public String deleteEmployee(String id) {
        return "Jane Doe";
    }
}
