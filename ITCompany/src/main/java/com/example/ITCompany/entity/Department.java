package com.example.ITCompany.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "DEPARTMENT")
public class Department {
    @Id
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    @Column(name = "DEPARTMENT_LEADER_NAME", unique = true)
    private String departmentLeaderName;

    @Column(name = "NUMBER_OF_EMPLOYEES")
    private Integer numberOfEmployees;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentLeaderName() {
        return departmentLeaderName;
    }

    public void setDepartmentLeaderName(String departmentLeaderName) {
        this.departmentLeaderName = departmentLeaderName;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }
}