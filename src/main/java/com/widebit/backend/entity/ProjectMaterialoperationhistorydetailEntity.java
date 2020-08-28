package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_materialoperationhistorydetail", schema = "egenecloud", catalog = "")
public class ProjectMaterialoperationhistorydetailEntity {
    private int id;
    private int materialId;
    private String productStatus;
    private String invoiceStatus;
    private double money;
    private String operator;
    private int operationId;
    private int count;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "materialID")
    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    @Basic
    @Column(name = "product_status")
    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    @Basic
    @Column(name = "invoice_status")
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @Basic
    @Column(name = "money")
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectMaterialoperationhistorydetailEntity that = (ProjectMaterialoperationhistorydetailEntity) o;
        return id == that.id &&
                materialId == that.materialId &&
                Double.compare(that.money, money) == 0 &&
                Objects.equals(productStatus, that.productStatus) &&
                Objects.equals(invoiceStatus, that.invoiceStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, materialId, productStatus, invoiceStatus, money);
    }

    @Basic
    @Column(name = "operator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "operation_id")
    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    @Basic
    @Column(name = "count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
