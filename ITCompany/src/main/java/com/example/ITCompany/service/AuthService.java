package com.example.ITCompany.service;

import com.example.ITCompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean userExists(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }
}