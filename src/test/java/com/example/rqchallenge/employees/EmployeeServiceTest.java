package com.example.rqchallenge.employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeApiService employeeApiService;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee.Builder().build();
    }

    @Test
    void findAllEmployees_Success() {
        when(employeeApiService.getAllEmployees()).thenReturn(Collections.singletonList(employee));
        List<Employee> result = employeeService.findAllEmployees();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(employeeApiService).getAllEmployees();
    }

    @Test
    void searchEmployeesByName_OneMatch_Success() {
        employee.setEmployeeName("matchingName");

        when(employeeApiService.getAllEmployees()).thenReturn(Collections.singletonList(employee));
        List<Employee> result = employeeService.searchEmployeesByName("matching");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(employeeApiService).getAllEmployees();
    }

    @Test
    void searchEmployeesByName_NoMatch_EmptyArray() {
        employee.setEmployeeName("matchingName");

        when(employeeApiService.getAllEmployees()).thenReturn(Collections.singletonList(employee));
        List<Employee> result = employeeService.searchEmployeesByName("notAMatch");
        assertTrue(result.isEmpty());
        verify(employeeApiService).getAllEmployees();
    }

    @Test
    void findEmployeeById_Match_Success() {
        when(employeeApiService.getEmployeeById("1")).thenReturn(employee);
        Optional<Employee> result = employeeService.findEmployeeById("1");
        assertTrue(result.isPresent());
        assertEquals(employee, result.get());
        verify(employeeApiService).getEmployeeById("1");
    }

    @Test
    void findEmployeeById_NoMatch_Empty() {
        when(employeeApiService.getEmployeeById("nonExistingId")).thenReturn(null);
        Optional<Employee> result = employeeService.findEmployeeById("nonExistingId");
        assertFalse(result.isPresent());
        verify(employeeApiService).getEmployeeById("nonExistingId");
    }

    @Test
    void findHighestSalary_Success() {
        when(employeeApiService.getAllEmployees()).thenReturn(Collections.singletonList(employee));
        int highestSalary = employeeService.findHighestSalary();

        assertEquals(employee.getEmployeeSalary(), highestSalary);
        verify(employeeApiService).getAllEmployees();
    }

    @Test
    void findHighestSalary_NoEmployees_ReturnZero() {
        when(employeeApiService.getAllEmployees()).thenReturn(Collections.emptyList());
        int highestSalary = employeeService.findHighestSalary();
        assertEquals(0, highestSalary);
        verify(employeeApiService).getAllEmployees();
    }

    @Test
    void findTopTenHighestEarningEmployeeNames_Over10_Success() {
        List<Employee> employees = generateRandomEmployees(12);

        when(employeeApiService.getAllEmployees()).thenReturn(employees);
        List<String> topEarners = employeeService.findTopTenHighestEarningEmployeeNames();
        assertEquals(10, topEarners.size());

        // Sort the original list of employees by salary in descending order
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getEmployeeSalary).reversed())
                .limit(10)
                .collect(Collectors.toList());

        // Assert that each employee salary is greater than or equal to the employee
        // salary after his
        for (int i = 0; i < sortedEmployees.size() - 1; i++) {
            int currentSalary = sortedEmployees.get(i).getEmployeeSalary();
            int nextSalary = sortedEmployees.get(i + 1).getEmployeeSalary();
            assertTrue(currentSalary >= nextSalary, "Employee salaries are not in descending order");
        }

        verify(employeeApiService).getAllEmployees();
    }

    @Test
    void findTopTenHighestEarningEmployeeNames_LessThan10_Success() {

        List<Employee> employees = generateRandomEmployees(8);

        when(employeeApiService.getAllEmployees()).thenReturn(employees);
        List<String> topEarners = employeeService.findTopTenHighestEarningEmployeeNames();
        assertEquals(8, topEarners.size());

        // Sort the original list of employees by salary in descending order
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getEmployeeSalary).reversed())
                .limit(10)
                .collect(Collectors.toList());

        // Assert that each employee salary is greater than or equal to the employee
        // salary after his
        for (int i = 0; i < sortedEmployees.size() - 1; i++) {
            int currentSalary = sortedEmployees.get(i).getEmployeeSalary();
            int nextSalary = sortedEmployees.get(i + 1).getEmployeeSalary();
            assertTrue(currentSalary >= nextSalary, "Employee salaries are not in descending order");
        }

        verify(employeeApiService).getAllEmployees();
    }

    @Test
    void createEmployee_Valid_Success() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", "50000");
        employeeInput.put("age", "30");

        when(employeeApiService.createEmployee(employeeInput)).thenReturn(employee);
        Employee result = employeeService.createEmployee(employeeInput);
        assertNotNull(result);
        verify(employeeApiService).createEmployee(employeeInput);
    }

    @Test
    void createEmployee_MissingValues_ThrowsIllegalArgumentException() {
        Map<String, Object> invalidInput = new HashMap<>();
        invalidInput.put("name", "John Doe");

        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(invalidInput);
        });
    }

    @Test
    void createEmployee_InvalidType_ThrowsIllegalArgumentException() {
        Map<String, Object> invalidInput = new HashMap<>();
        invalidInput.put("name", "John Doe");
        invalidInput.put("salary", "$50000");
        invalidInput.put("age", "30");

        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(invalidInput);
        });
    }

    @Test
    void deleteEmployeeById_Match_Success() {
        String id = "1";
        when(employeeApiService.deleteEmployee(id)).thenReturn(id);
        Optional<String> result = employeeService.deleteEmployeeById(id);
        assertTrue(result.isPresent());
        assertEquals(id, result.get());
        verify(employeeApiService).deleteEmployee(id);
    }

    @Test
    void deleteEmployeeById_NoMatch_Empty() {
        String nonExistingId = "nonExistingId";
        when(employeeApiService.deleteEmployee(nonExistingId)).thenReturn(null);
        Optional<String> result = employeeService.deleteEmployeeById(nonExistingId);
        assertFalse(result.isPresent());
        verify(employeeApiService).deleteEmployee(nonExistingId);
    }

    private List<Employee> generateRandomEmployees(int numberOfEmployees) {
        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfEmployees; i++) {
            Employee employee = new Employee.Builder()
                    .id(random.nextLong())
                    .employeeName("Employee " + i)
                    .employeeSalary(random.nextInt(100000))
                    .employeeAge(20 + random.nextInt(40))
                    .profileImage("image" + i + ".jpg")
                    .build();
            employees.add(employee);
        }

        return employees;
    }

}
