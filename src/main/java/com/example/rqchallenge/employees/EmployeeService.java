package com.example.rqchallenge.employees;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing employees.
 */
@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeApiService employeeApiService;

    public EmployeeService(@NonNull EmployeeApiService employeeApiService) {
        this.employeeApiService = employeeApiService;
        logger.info("EmployeeService initialized with EmployeeApiService");
    }

    public List<Employee> findAllEmployees() {
        logger.info("Fetching all employees");
        return employeeApiService.getAllEmployees();
    }

    public List<Employee> searchEmployeesByName(@NonNull String searchString) {
        logger.info("Searching employees by name: {}", searchString);
        return employeeApiService.getAllEmployees().stream()
                .filter(employee -> employee.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Employee> findEmployeeById(@NonNull String id) {
        logger.info("Finding employee by ID: {}", id);
        return Optional.ofNullable(employeeApiService.getEmployeeById(id));
    }

    public int findHighestSalary() {
        logger.info("Finding the highest salary among all employees");
        return employeeApiService.getAllEmployees().stream()
                .mapToInt(Employee::getEmployeeSalary)
                .max()
                .orElse(0);
    }

    public List<String> findTopTenHighestEarningEmployeeNames() {
        logger.info("Finding the top ten highest earning employee names");
        return employeeApiService.getAllEmployees().stream()
                .sorted(Comparator.comparingInt(Employee::getEmployeeSalary).reversed())
                .limit(10)
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());
    }

    public Employee createEmployee(@NonNull Map<String, Object> employeeInput) {
        logger.info("Creating a new employee with input: {}", employeeInput);
        validateEmployeeInput(employeeInput);
        return employeeApiService.createEmployee(employeeInput);
    }

    public Optional<String> deleteEmployeeById(@NonNull String id) {
        logger.info("Deleting employee with ID: {}", id);
        return Optional.ofNullable(employeeApiService.deleteEmployee(id));
    }

    /**
     * Validates the employee input data.
     * 
     * @param employeeInput a map containing employee data
     */
    private void validateEmployeeInput(Map<String, Object> employeeInput) {
        // Validate required fields
        if (!employeeInput.containsKey("name") ||
                !employeeInput.containsKey("salary") ||
                !employeeInput.containsKey("age")) {
            throw new IllegalArgumentException("Missing required fields");
        }

        // Validate that salary and age are integers
        try {
            Integer.parseInt(employeeInput.get("salary").toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Salary must be an integer");
        }

        try {
            Integer.parseInt(employeeInput.get("age").toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age must be an integer");
        }
    }
}
