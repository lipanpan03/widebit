package com.widebit.backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "project_materialoperationhistory", schema = "egenecloud", catalog = "")
public class ProjectMaterialoperationhistoryEntity {
    private int id;
    private String type;
    private String status;
    private double totalMoney;
    private int totalAmount;
    private String operator;
    private Timestamp operateTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "total_money")
    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Basic
    @Column(name = "total_amount")
    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
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
    @Column(name = "operate_time")
    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectMaterialoperationhistoryEntity that = (ProjectMaterialoperationhistoryEntity) o;
        return id == that.id &&
                Double.compare(that.totalMoney, totalMoney) == 0 &&
                totalAmount == that.totalAmount &&
                Objects.equals(type, that.type) &&
                Objects.equals(status, that.status) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(operateTime, that.operateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, status, totalMoney, totalAmount, operator, operateTime);
    }
}
