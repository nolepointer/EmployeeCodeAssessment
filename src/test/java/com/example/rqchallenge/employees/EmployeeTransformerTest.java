package com.example.rqchallenge.employees;

import org.junit.jupiter.api.Test;

import com.example.rqchallenge.exceptions.ExternalApiException;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTransformerTest {

    @Test
    void transform_ValidDummyEmployeeDto_Success() {
        DummyEmployeeDto dummyEmployeeDto = new DummyEmployeeDto();
        dummyEmployeeDto.setId("1");
        dummyEmployeeDto.setEmployee_name("John Doe");
        dummyEmployeeDto.setEmployee_salary("50000");
        dummyEmployeeDto.setEmployee_age("30");
        dummyEmployeeDto.setProfile_image("image.jpg");

        Employee result = EmployeeTransformer.transform(dummyEmployeeDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getEmployeeName());
        assertEquals(50000, result.getEmployeeSalary());
        assertEquals(30, result.getEmployeeAge());
        assertEquals("image.jpg", result.getProfileImage());
    }

    @Test
    void transform_NullDummyEmployeeDto_ThrowsExternalApiException() {
        assertThrows(ExternalApiException.class, () -> {
            EmployeeTransformer.transform(null);
        });
    }

    @Test
    void transform_InvalidSalary_ThrowsExternalApiException() {
        DummyEmployeeDto dummyEmployeeDto = new DummyEmployeeDto();
        dummyEmployeeDto.setId("1");
        dummyEmployeeDto.setEmployee_name("John Doe");
        dummyEmployeeDto.setEmployee_salary("invalidSalary");
        dummyEmployeeDto.setEmployee_age("30");
        dummyEmployeeDto.setProfile_image("image.jpg");

        assertThrows(ExternalApiException.class, () -> {
            EmployeeTransformer.transform(dummyEmployeeDto);
        });
    }

    @Test
    void transform_InvalidAge_ThrowsExternalApiException() {
        DummyEmployeeDto dummyEmployeeDto = new DummyEmployeeDto();
        dummyEmployeeDto.setId("1");
        dummyEmployeeDto.setEmployee_name("John Doe");
        dummyEmployeeDto.setEmployee_salary("50000");
        dummyEmployeeDto.setEmployee_age("invalidAge");
        dummyEmployeeDto.setProfile_image("image.jpg");

        assertThrows(ExternalApiException.class, () -> {
            EmployeeTransformer.transform(dummyEmployeeDto);
        });
    }
}
