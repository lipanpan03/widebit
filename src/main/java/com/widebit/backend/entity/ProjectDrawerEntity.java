package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_drawer", schema = "egenecloud", catalog = "")
public class ProjectDrawerEntity {
    private int id;
    private int fridgeId;
    private int drawerOrder;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fridge_id")
    public int getFridgeId() {
        return fridgeId;
    }

    public void setFridgeId(int fridgeId) {
        this.fridgeId = fridgeId;
    }

    @Basic
    @Column(name = "drawer_order")
    public int getDrawerOrder() {
        return drawerOrder;
    }

    public void setDrawerOrder(int drawerOrder) {
        this.drawerOrder = drawerOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDrawerEntity that = (ProjectDrawerEntity) o;
        return id == that.id &&
                fridgeId == that.fridgeId &&
                drawerOrder == that.drawerOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fridgeId, drawerOrder);
    }
}
