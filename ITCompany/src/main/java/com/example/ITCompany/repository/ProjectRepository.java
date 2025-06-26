package com.example.ITCompany.repository;

import com.example.ITCompany.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    boolean existsByProjectNameAndStartProjectAndEndProject(
            String projectName,
            Date startProject,
            Date endProject);
    @Query("SELECT COUNT(p) > 0 FROM Project p WHERE " +
            "p.projectName = :projectName AND " +
            "p.startProject = :startProject AND " +
            "p.endProject = :endProject AND " +
            "p.projectId != :excludeProjectId")
    boolean existsByProjectNameAndDatesAndIdNot(
            @Param("projectName") String projectName,
            @Param("startProject") Date startProject,
            @Param("endProject") Date endProject,
            @Param("excludeProjectId") Integer excludeProjectId);

}