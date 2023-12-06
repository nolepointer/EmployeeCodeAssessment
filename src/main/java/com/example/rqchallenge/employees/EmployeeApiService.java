package com.example.rqchallenge.employees;

import java.util.List;
import java.util.Map;
import org.springframework.lang.NonNull;

/**
 * Interface for employee-related API services.
 */
public interface EmployeeApiService {

    /**
     * Retrieves a list of all employees.
     *
     * @return a list of {@link Employee} objects
     */
    List<Employee> getAllEmployees();

    /**
     * Retrieves a specific employee by their ID.
     *
     * @param id the unique identifier of the employee
     * @return the {@link Employee} object with the specified ID
     */
    Employee getEmployeeById(String id);

    /**
     * Creates a new employee with the given input data.
     *
     * @param employeeInput a map containing the employee's data
     * @return the newly created {@link Employee} object
     */
    Employee createEmployee(@NonNull Map<String, Object> employeeInput);

    /**
     * Deletes an employee by their ID.
     *
     * @param id the unique identifier of the employee to be deleted
     * @return the id of the successfully deleted employee
     */
    String deleteEmployee(String id);
}
