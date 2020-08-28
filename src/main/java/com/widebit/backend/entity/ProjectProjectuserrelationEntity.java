package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_projectuserrelation", schema = "egenecloud", catalog = "")
public class ProjectProjectuserrelationEntity {
    private int id;
    private String projectId;
    private String userId;
    private byte owner;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "project_id")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "owner")
    public byte getOwner() {
        return owner;
    }

    public void setOwner(byte owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectProjectuserrelationEntity that = (ProjectProjectuserrelationEntity) o;
        return id == that.id &&
                owner == that.owner &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, userId, owner);
    }
}
