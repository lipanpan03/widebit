package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_dish", schema = "egenecloud", catalog = "")
public class ProjectDishEntity {
    private int id;
    private int dishx;
    private int dishy;
    private int drawerId;
    private int dishOrder;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dishx")
    public int getDishx() {
        return dishx;
    }

    public void setDishx(int dishx) {
        this.dishx = dishx;
    }

    @Basic
    @Column(name = "dishy")
    public int getDishy() {
        return dishy;
    }

    public void setDishy(int dishy) {
        this.dishy = dishy;
    }

    @Basic
    @Column(name = "drawer_id")
    public int getDrawerId() {
        return drawerId;
    }

    public void setDrawerId(int drawerId) {
        this.drawerId = drawerId;
    }

    @Basic
    @Column(name = "dish_order")
    public int getDishOrder() {
        return dishOrder;
    }

    public void setDishOrder(int dishOrder) {
        this.dishOrder = dishOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDishEntity that = (ProjectDishEntity) o;
        return id == that.id &&
                dishx == that.dishx &&
                dishy == that.dishy &&
                drawerId == that.drawerId &&
                dishOrder == that.dishOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dishx, dishy, drawerId, dishOrder);
    }
}
