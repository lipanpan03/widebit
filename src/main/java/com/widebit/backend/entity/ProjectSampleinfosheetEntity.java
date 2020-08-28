package com.widebit.backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "project_sampleinfosheet", schema = "egenecloud", catalog = "")
public class ProjectSampleinfosheetEntity {
    private int id;
    private String name;
    private String labId;
    private String status;
    private String sampleStatus;
    private String sampleStatusNote;
    private String sampleType;
    private String sampleTypeNote;
    private String specialProcess;
    private String specialProcessNote;
    private String freezeThaw;
    private String freezeThawNote;
    private String storeMethod;
    private String storedTime;
    private String infectivity;
    private String infectiousNote;
    private String sampleTotalCount;
    private String sampleProcessCount;
    private String needReturn;
    private String dataSize;
    private String bioDuplication;
    private String preprocess;
    private String expressCompany;
    private String expressId;
    private Timestamp createTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "lab_id")
    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
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
    @Column(name = "sample_status")
    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    @Basic
    @Column(name = "sample_status_note")
    public String getSampleStatusNote() {
        return sampleStatusNote;
    }

    public void setSampleStatusNote(String sampleStatusNote) {
        this.sampleStatusNote = sampleStatusNote;
    }

    @Basic
    @Column(name = "sample_type")
    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    @Basic
    @Column(name = "sample_type_note")
    public String getSampleTypeNote() {
        return sampleTypeNote;
    }

    public void setSampleTypeNote(String sampleTypeNote) {
        this.sampleTypeNote = sampleTypeNote;
    }

    @Basic
    @Column(name = "special_process")
    public String getSpecialProcess() {
        return specialProcess;
    }

    public void setSpecialProcess(String specialProcess) {
        this.specialProcess = specialProcess;
    }

    @Basic
    @Column(name = "special_process_note")
    public String getSpecialProcessNote() {
        return specialProcessNote;
    }

    public void setSpecialProcessNote(String specialProcessNote) {
        this.specialProcessNote = specialProcessNote;
    }

    @Basic
    @Column(name = "freeze_thaw")
    public String getFreezeThaw() {
        return freezeThaw;
    }

    public void setFreezeThaw(String freezeThaw) {
        this.freezeThaw = freezeThaw;
    }

    @Basic
    @Column(name = "freeze_thaw_note")
    public String getFreezeThawNote() {
        return freezeThawNote;
    }

    public void setFreezeThawNote(String freezeThawNote) {
        this.freezeThawNote = freezeThawNote;
    }

    @Basic
    @Column(name = "store_method")
    public String getStoreMethod() {
        return storeMethod;
    }

    public void setStoreMethod(String storeMethod) {
        this.storeMethod = storeMethod;
    }

    @Basic
    @Column(name = "stored_time")
    public String getStoredTime() {
        return storedTime;
    }

    public void setStoredTime(String storedTime) {
        this.storedTime = storedTime;
    }

    @Basic
    @Column(name = "infectivity")
    public String getInfectivity() {
        return infectivity;
    }

    public void setInfectivity(String infectivity) {
        this.infectivity = infectivity;
    }

    @Basic
    @Column(name = "infectious_note")
    public String getInfectiousNote() {
        return infectiousNote;
    }

    public void setInfectiousNote(String infectiousNote) {
        this.infectiousNote = infectiousNote;
    }

    @Basic
    @Column(name = "sample_total_count")
    public String getSampleTotalCount() {
        return sampleTotalCount;
    }

    public void setSampleTotalCount(String sampleTotalCount) {
        this.sampleTotalCount = sampleTotalCount;
    }

    @Basic
    @Column(name = "sample_process_count")
    public String getSampleProcessCount() {
        return sampleProcessCount;
    }

    public void setSampleProcessCount(String sampleProcessCount) {
        this.sampleProcessCount = sampleProcessCount;
    }

    @Basic
    @Column(name = "need_return")
    public String getNeedReturn() {
        return needReturn;
    }

    public void setNeedReturn(String needReturn) {
        this.needReturn = needReturn;
    }

    @Basic
    @Column(name = "data_size")
    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    @Basic
    @Column(name = "bio_duplication")
    public String getBioDuplication() {
        return bioDuplication;
    }

    public void setBioDuplication(String bioDuplication) {
        this.bioDuplication = bioDuplication;
    }

    @Basic
    @Column(name = "preprocess")
    public String getPreprocess() {
        return preprocess;
    }

    public void setPreprocess(String preprocess) {
        this.preprocess = preprocess;
    }

    @Basic
    @Column(name = "express_company")
    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    @Basic
    @Column(name = "express_id")
    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectSampleinfosheetEntity that = (ProjectSampleinfosheetEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(labId, that.labId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(sampleStatus, that.sampleStatus) &&
                Objects.equals(sampleStatusNote, that.sampleStatusNote) &&
                Objects.equals(sampleType, that.sampleType) &&
                Objects.equals(sampleTypeNote, that.sampleTypeNote) &&
                Objects.equals(specialProcess, that.specialProcess) &&
                Objects.equals(specialProcessNote, that.specialProcessNote) &&
                Objects.equals(freezeThaw, that.freezeThaw) &&
                Objects.equals(freezeThawNote, that.freezeThawNote) &&
                Objects.equals(storeMethod, that.storeMethod) &&
                Objects.equals(storedTime, that.storedTime) &&
                Objects.equals(infectivity, that.infectivity) &&
                Objects.equals(infectiousNote, that.infectiousNote) &&
                Objects.equals(sampleTotalCount, that.sampleTotalCount) &&
                Objects.equals(sampleProcessCount, that.sampleProcessCount) &&
                Objects.equals(needReturn, that.needReturn) &&
                Objects.equals(dataSize, that.dataSize) &&
                Objects.equals(bioDuplication, that.bioDuplication) &&
                Objects.equals(preprocess, that.preprocess) &&
                Objects.equals(expressCompany, that.expressCompany) &&
                Objects.equals(expressId, that.expressId) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, labId, status, sampleStatus, sampleStatusNote, sampleType, sampleTypeNote, specialProcess, specialProcessNote, freezeThaw, freezeThawNote, storeMethod, storedTime, infectivity, infectiousNote, sampleTotalCount, sampleProcessCount, needReturn, dataSize, bioDuplication, preprocess, expressCompany, expressId, createTime);
    }
}
