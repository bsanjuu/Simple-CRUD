package com.bsanju.simplecrud.controller;

import com.bsanju.simplecrud.entity.Employee;
import com.bsanju.simplecrud.repository.EmployeeRepository;
import com.bsanju.simplecrud.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;

    }
    @GetMapping
    public String hello(){
        return "Home Page";
    }
    @GetMapping("/all")
    public List<Employee> getAll(){
        return employeeService.findAll();
    }
    @PostMapping
    public Employee create(@RequestBody Employee employee){
       return employeeService.save(employee);
    }
    @GetMapping("/{id}")
    public Employee getById(@PathVariable int id) {
        return employeeService.findById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        employeeService.deleteById(id);
    }
    @GetMapping("/name/{name}")
    public Employee getByName(@PathVariable String name) {
        return employeeService.findByName(name);
    }


}
