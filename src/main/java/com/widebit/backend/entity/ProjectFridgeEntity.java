package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_fridge", schema = "egenecloud", catalog = "")
public class ProjectFridgeEntity {
    private int id;
    private String environment;
    private int drawerCount;
    private int dishxCount;
    private int dishyCount;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "environment")
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Basic
    @Column(name = "drawer_count")
    public int getDrawerCount() {
        return drawerCount;
    }

    public void setDrawerCount(int drawerCount) {
        this.drawerCount = drawerCount;
    }

    @Basic
    @Column(name = "dishx_count")
    public int getDishxCount() {
        return dishxCount;
    }

    public void setDishxCount(int dishxCount) {
        this.dishxCount = dishxCount;
    }

    @Basic
    @Column(name = "dishy_count")
    public int getDishyCount() {
        return dishyCount;
    }

    public void setDishyCount(int dishyCount) {
        this.dishyCount = dishyCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectFridgeEntity that = (ProjectFridgeEntity) o;
        return id == that.id &&
                drawerCount == that.drawerCount &&
                dishxCount == that.dishxCount &&
                dishyCount == that.dishyCount &&
                Objects.equals(environment, that.environment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, environment, drawerCount, dishxCount, dishyCount);
    }
}
