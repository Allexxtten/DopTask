package com.example.ITCompany.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PARTICIPATION")
public class Participation {
    @EmbeddedId
    private ParticipationId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    public ParticipationId getId() {
        return id;
    }

    public void setId(ParticipationId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

