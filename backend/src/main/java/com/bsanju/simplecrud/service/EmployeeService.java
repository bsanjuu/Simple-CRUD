package com.bsanju.simplecrud.service;

import com.bsanju.simplecrud.entity.Employee;
import com.bsanju.simplecrud.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(int id) {
        return employeeRepository.findById((long) id).orElse(null);
    }

    public Employee findByName(String name) {
        return employeeRepository.findAll().stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);

    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteById(int id) {
        employeeRepository.deleteById((long) id);
    }

}
