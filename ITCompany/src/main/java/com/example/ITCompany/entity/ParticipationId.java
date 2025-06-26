package com.example.ITCompany.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ParticipationId implements Serializable {
    private Integer employeeId;
    private Integer projectId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
