package com.example.ITCompany.repository;

import com.example.ITCompany.entity.Department;
import com.example.ITCompany.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    long countByDepartment(Department department);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByEmailAndPassword(String email, String password);
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department")
    List<Employee> findAllWithDepartments();
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department LEFT JOIN FETCH e.post")
    List<Employee> findAllWithDetails();
}