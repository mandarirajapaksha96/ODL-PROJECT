package com.example.demo.service;


import com.example.demo.model.Employee;
import com.example.demo.repo.EmployeeRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }
    public List<Employee> getEmployees() {
        return repository.findAll();
    }
    public Optional<Employee> findEmployeeById(Long id) {
        return repository.findById(id);
    }

}
