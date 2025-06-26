package com.example.ITCompany.repository;

import com.example.ITCompany.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    Optional<Department> findById(String departmentName);
    Department findByDepartmentLeaderName(String leaderName);
}
