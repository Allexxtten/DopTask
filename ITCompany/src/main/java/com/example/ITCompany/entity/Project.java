package com.example.ITCompany.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Table(name = "PROJECT")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Integer projectId;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "START_PROJECT")
    private Date startProject;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "END_PROJECT")
    private Date endProject;

    @Column(name = "PROJECT_STATUS")
    private String projectStatus;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartProject() {
        return startProject;
    }

    public void setStartProject(Date startProject) {
        this.startProject = startProject;
    }

    public Date getEndProject() {
        return endProject;
    }

    public void setEndProject(Date endProject) {
        this.endProject = endProject;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
}