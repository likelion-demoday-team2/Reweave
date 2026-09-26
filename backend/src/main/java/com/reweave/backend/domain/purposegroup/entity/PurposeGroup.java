package com.reweave.backend.domain.purposegroup.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "purposegroups")
public class PurposeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purpose_group_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "purpose_name", nullable = false, length = 255)
    private String purposeName;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

    protected PurposeGroup() {
    }

    public PurposeGroup(Long userId, String purposeName, String status) {
        this.userId = userId;
        this.purposeName = purposeName;
        this.status = status;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPurposeName() {
        return purposeName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void update(String purposeName) {
        this.purposeName = purposeName;
        this.modifiedDate = LocalDateTime.now();
    }

    public void updateStatus(String status) {
        this.status = status;
        this.modifiedDate = LocalDateTime.now();
    }
}
