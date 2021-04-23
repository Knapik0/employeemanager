package com.example.employeemanager.repo;

import com.example.employeemanager.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepoTest {

    @Autowired
    private EmployeeRepo underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void returnTrueIfEmailExists() {
        //given
        String email = "jan@example.com";
        Employee employee = new Employee(
                1,
                "Jan",
                email,
                "Mechsnik",
                "505",
                "obraz",
                "333");

        underTest.save(employee);

        //when
        Boolean actual = underTest.selectExistsEmail(email);

        //then
        assertEquals(true, actual);
    }
    @Test
    void returnFalseIfEmailDoesntExist() {
        //given
        String email = "jan@example.com";

        //when
        Boolean actual = underTest.selectExistsEmail(email);

        //then
        assertEquals(false, actual);
    }
}