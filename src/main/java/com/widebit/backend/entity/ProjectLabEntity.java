package com.widebit.backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "project_lab", schema = "egenecloud", catalog = "")
public class ProjectLabEntity {
    private int id;
    private String labId;
    private String projectId;
    private String productId;
    private String name;
    private Timestamp startTime;
    private String status;
    private String qualityReport;
    private byte notify;
    private Timestamp launchTime;
    private String customerNote;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lab_id")
    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
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

    @Basic
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
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
    @Column(name = "quality_report")
    public String getQualityReport() {
        return qualityReport;
    }

    public void setQualityReport(String qualityReport) {
        this.qualityReport = qualityReport;
    }

    @Basic
    @Column(name = "notify")
    public byte getNotify() {
        return notify;
    }

    public void setNotify(byte notify) {
        this.notify = notify;
    }

    @Basic
    @Column(name = "launch_time")
    public Timestamp getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(Timestamp launchTime) {
        this.launchTime = launchTime;
    }

    @Basic
    @Column(name = "customer_note")
    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectLabEntity labEntity = (ProjectLabEntity) o;
        return id == labEntity.id &&
                notify == labEntity.notify &&
                Objects.equals(labId, labEntity.labId) &&
                Objects.equals(projectId, labEntity.projectId) &&
                Objects.equals(productId, labEntity.productId) &&
                Objects.equals(name, labEntity.name) &&
                Objects.equals(startTime, labEntity.startTime) &&
                Objects.equals(status, labEntity.status) &&
                Objects.equals(qualityReport, labEntity.qualityReport) &&
                Objects.equals(launchTime, labEntity.launchTime) &&
                Objects.equals(customerNote, labEntity.customerNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, labId, projectId, productId, name, startTime, status, qualityReport, notify, launchTime, customerNote);
    }
}
