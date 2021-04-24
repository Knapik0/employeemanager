package com.example.employeemanager.service;

import com.example.employeemanager.exception.BadRequestException;
import com.example.employeemanager.exception.UserNotFoundException;
import com.example.employeemanager.model.Employee;
import com.example.employeemanager.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee) {
        boolean emailExists = employeeRepo.selectExistsEmail(employee.getEmail());
        boolean employeeExists = employeeRepo.existsById(employee.getId());

        // if email exists
        if (emailExists) {
            //  and employee doesnt exists or other employee has given email throws exception
            if (employeeExists) {
                String previousEmail = employeeRepo.getEmployeeEmailById(employee.getId());
                boolean employeeHadDifferentEmail = !previousEmail.equals(employee.getEmail());
                if (employeeHadDifferentEmail) {
                    throw new BadRequestException("Email " + employee.getEmail() + " already exists");
                }
            } else {
                    throw new BadRequestException("Email " + employee.getEmail() + " already exists");

                }

            }
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id: " + id + " was not found"));
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    public List<Employee> searchEmployees(String key) {
        List<Employee> employees = new ArrayList<>();

        for(Employee employee : findAllEmployees()) {
             if(employee.getName().toLowerCase().contains(key.toLowerCase())
                     || employee.getEmail().toLowerCase().contains(key.toLowerCase())
                     || employee.getPhone().toLowerCase().contains(key.toLowerCase())
                     || employee.getJobTitle().toLowerCase().contains(key.toLowerCase())) {
                 employees.add(employee);
             }
        }
        return employees;
    }
}
