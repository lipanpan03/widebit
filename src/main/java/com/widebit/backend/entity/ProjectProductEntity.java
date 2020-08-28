package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_product", schema = "egenecloud", catalog = "")
public class ProjectProductEntity {
    private int id;
    private String productId;
    private String name;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_id")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectProductEntity that = (ProjectProductEntity) o;
        return id == that.id &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name);
    }
}
