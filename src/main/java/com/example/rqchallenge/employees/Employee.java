package com.example.rqchallenge.employees;

import java.util.Objects;

import org.springframework.lang.NonNull;

public class Employee {

    private long id;
    private String employeeName;
    private int employeeSalary;
    private int employeeAge;
    private String profileImage;

    private Employee(Builder builder) {
        this.id = builder.id;
        this.employeeName = builder.employeeName;
        this.employeeSalary = builder.employeeSalary;
        this.employeeAge = builder.employeeAge;
        this.profileImage = builder.profileImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(@NonNull String employeeName) {
        this.employeeName = employeeName;
    }

    public int getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(int employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public int getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeName='" + employeeName + '\'' +
                ", employeeSalary=" + employeeSalary +
                ", employeeAge=" + employeeAge +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Double.compare(employee.employeeSalary, employeeSalary) == 0 &&
                employeeAge == employee.employeeAge &&
                Objects.equals(employeeName, employee.employeeName) &&
                Objects.equals(profileImage, employee.profileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeName, employeeSalary, employeeAge, profileImage);
    }

    public static class Builder {
        private long id;
        private String employeeName;
        private int employeeSalary;
        private int employeeAge;
        private String profileImage;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder employeeName(@NonNull String employeeName) {
            this.employeeName = employeeName;
            return this;
        }

        public Builder employeeSalary(int employeeSalary) {
            this.employeeSalary = employeeSalary;
            return this;
        }

        public Builder employeeAge(int employeeAge) {
            this.employeeAge = employeeAge;
            return this;
        }

        public Builder profileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }
}
