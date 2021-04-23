package com.example.employeemanager.service;

import com.example.employeemanager.model.Employee;
import com.example.employeemanager.repo.EmployeeRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;
    private EmployeeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new EmployeeService(employeeRepo);
    }

    @Test
    void canAddEmployee() {
        // given
        Employee employee = new Employee(
                1,
                "Jan",
                "jan@example.com",
                "Mechsnik",
                "505",
                "obraz",
                "333");
        // when
        underTest.addEmployee(employee);

        // then
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepo).save(employeeArgumentCaptor.capture());

        Employee capturedEmployee = employeeArgumentCaptor.getValue();

        assertEquals(employee, capturedEmployee);
    }

    @Test
    void canFindAllEmployees() {
        //when
        underTest.findAllEmployees();

        // then
        verify(employeeRepo).findAll();
    }

    @Test
    void canFindEmployeeById() {

    }

    @Test
    void canUpdateEmployee() {
    }

    @Test
    void canDeleteEmployee() {
    }

    @Test
    void canSearchEmployees() {
    }
}