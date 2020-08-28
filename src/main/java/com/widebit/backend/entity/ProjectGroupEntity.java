package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_group", schema = "egenecloud", catalog = "")
public class ProjectGroupEntity {
    private int id;
    private String groupId;
    private String memeberId;
    private String auth;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "memeber_id")
    public String getMemeberId() {
        return memeberId;
    }

    public void setMemeberId(String memeberId) {
        this.memeberId = memeberId;
    }

    @Basic
    @Column(name = "auth")
    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectGroupEntity that = (ProjectGroupEntity) o;
        return id == that.id &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(memeberId, that.memeberId) &&
                Objects.equals(auth, that.auth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, memeberId, auth);
    }
}
