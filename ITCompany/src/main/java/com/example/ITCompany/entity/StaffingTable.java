package com.example.ITCompany.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "STAFFING_TABLE")
public class StaffingTable {
    @Id
    @Column(name = "POST")
    private String post;

    @Column(name = "SALARY")
    private Integer salary;

    @ManyToOne
    @JoinColumn(name = "RIGHT_ID", referencedColumnName = "RIGHT_ID")
    private AccessRights accessRights;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public AccessRights getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(AccessRights accessRights) {
        this.accessRights = accessRights;
    }
}
