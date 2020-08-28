package com.widebit.backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "project_project", schema = "egenecloud", catalog = "")
public class ProjectProjectEntity {
    private int id;
    private String projectId;
    private String name;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp downPaymentTime;
    private Timestamp accountDueTime;
    private String paymentType;
    private String projectType;
    private int projectDuration;
    private String projectStatus;
    private byte canceled;
    private double totalPayment;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "down_payment_time")
    public Timestamp getDownPaymentTime() {
        return downPaymentTime;
    }

    public void setDownPaymentTime(Timestamp downPaymentTime) {
        this.downPaymentTime = downPaymentTime;
    }

    @Basic
    @Column(name = "account_due_time")
    public Timestamp getAccountDueTime() {
        return accountDueTime;
    }

    public void setAccountDueTime(Timestamp accountDueTime) {
        this.accountDueTime = accountDueTime;
    }

    @Basic
    @Column(name = "payment_type")
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Basic
    @Column(name = "project_type")
    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    @Basic
    @Column(name = "project_duration")
    public int getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(int projectDuration) {
        this.projectDuration = projectDuration;
    }

    @Basic
    @Column(name = "project_status")
    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    @Basic
    @Column(name = "canceled")
    public byte getCanceled() {
        return canceled;
    }

    public void setCanceled(byte canceled) {
        this.canceled = canceled;
    }

    @Basic
    @Column(name = "total_payment")
    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectProjectEntity that = (ProjectProjectEntity) o;
        return id == that.id &&
                projectDuration == that.projectDuration &&
                canceled == that.canceled &&
                Double.compare(that.totalPayment, totalPayment) == 0 &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(downPaymentTime, that.downPaymentTime) &&
                Objects.equals(accountDueTime, that.accountDueTime) &&
                Objects.equals(paymentType, that.paymentType) &&
                Objects.equals(projectType, that.projectType) &&
                Objects.equals(projectStatus, that.projectStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, name, startTime, endTime, downPaymentTime, accountDueTime, paymentType, projectType, projectDuration, projectStatus, canceled, totalPayment);
    }
}
