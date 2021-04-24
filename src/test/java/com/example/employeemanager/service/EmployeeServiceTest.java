package com.example.employeemanager.service;

import com.example.employeemanager.exception.BadRequestException;
import com.example.employeemanager.exception.UserNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
    void canAddEmployeeIfEmailDoesntExist() {
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
    void cantAddEmployeeIfEmailExistsAndThrowsException() {
        // given
        Employee employee = new Employee(
                1,
                "Jan",
                "jan@example.com",
                "Mechsnik",
                "505",
                "obraz",
                "333");
        given(employeeRepo.selectExistsEmail(anyString())).willReturn(true);
        // when

        // then
        BadRequestException badRequestException = assertThrows(BadRequestException.class,
                                                    () -> underTest.addEmployee(employee));

        verify(employeeRepo, never()).save(any());

        assertEquals("Email jan@example.com already exists", badRequestException.getMessage());
    }

    @Test
    void canFindAllEmployees() {
        //when
        underTest.findAllEmployees();

        // then
        verify(employeeRepo).findAll();
    }

    @Test
    void canFindEmployeeByIdWhenIdExists() {
        // given
        Employee employee = new Employee(
                1,
                "Jan",
                "jan@example.com",
                "Mechsnik",
                "505",
                "obraz",
                "333");
        employeeRepo.save(employee);
        given(employeeRepo.findById(anyLong())).willReturn(Optional.of(employee));
        // when
        underTest.findEmployeeById(0L);
        // then

        verify(employeeRepo).findById(0L);
    }

    @Test
    void cantFindEmployeeByIdWhenIdDoesntExist() {
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

        // then
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
                () -> underTest.findEmployeeById(employee.getId()));

        assertEquals("User by id: 1 was not found", userNotFoundException.getMessage());
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