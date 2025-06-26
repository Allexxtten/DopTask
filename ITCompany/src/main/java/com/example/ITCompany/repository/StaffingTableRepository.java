package com.example.ITCompany.repository;

import com.example.ITCompany.entity.StaffingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StaffingTableRepository extends JpaRepository<StaffingTable, String> {
    Optional<StaffingTable> findById(String post);
}