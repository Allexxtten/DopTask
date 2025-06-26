package com.example.ITCompany.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ACCESS_RIGHTS")
public class AccessRights {
    @Id
    @Column(name = "RIGHT_ID")
    private Integer rightId;

    @Column(name = "RIGHT_NAME", unique = true)
    private String rightName;

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }
}
