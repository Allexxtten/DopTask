package com.example.ITCompany.repository;

import com.example.ITCompany.entity.Participation;
import com.example.ITCompany.entity.ParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, ParticipationId> {
    @Query("SELECT p.id.employeeId FROM Participation p WHERE p.id.projectId = :projectId")
    List<Integer> findEmployeeIdsByProjectId(@Param("projectId") Integer projectId);
}
