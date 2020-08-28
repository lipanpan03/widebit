package com.widebit.backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "project_sample", schema = "egenecloud", catalog = "")
public class ProjectSampleEntity {
    private int id;
    private String infoSheetId;
    private String name;
    private String spec;
    private String organ;
    private String weightOrConsistency;
    private String volume;
    private String processMethod;
    private String extractType;
    private String note;
    private String imageNote;
    private String storageStatus;
    private String status;
    private Timestamp arrivedTime;
    private String storageLocation;
    private Integer checkoutSheetId;
    private int fatherSampleId;
    private String concentration;
    private double total;
    private double remainder;
    private Timestamp returnTime;
    private Timestamp destoryTime;
    private String receiver;
    private String sampleRemark;
    private String sampleType;
    private String labId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "info_sheet_id")
    public String getInfoSheetId() {
        return infoSheetId;
    }

    public void setInfoSheetId(String infoSheetId) {
        this.infoSheetId = infoSheetId;
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
    @Column(name = "spec")
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Basic
    @Column(name = "organ")
    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    @Basic
    @Column(name = "weight_or_consistency")
    public String getWeightOrConsistency() {
        return weightOrConsistency;
    }

    public void setWeightOrConsistency(String weightOrConsistency) {
        this.weightOrConsistency = weightOrConsistency;
    }

    @Basic
    @Column(name = "volume")
    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Basic
    @Column(name = "process_method")
    public String getProcessMethod() {
        return processMethod;
    }

    public void setProcessMethod(String processMethod) {
        this.processMethod = processMethod;
    }

    @Basic
    @Column(name = "extract_type")
    public String getExtractType() {
        return extractType;
    }

    public void setExtractType(String extractType) {
        this.extractType = extractType;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "image_note")
    public String getImageNote() {
        return imageNote;
    }

    public void setImageNote(String imageNote) {
        this.imageNote = imageNote;
    }

    @Basic
    @Column(name = "storage_status")
    public String getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(String storageStatus) {
        this.storageStatus = storageStatus;
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
    @Column(name = "arrived_time")
    public Timestamp getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(Timestamp arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    @Basic
    @Column(name = "storage_location")
    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    @Basic
    @Column(name = "checkout_sheet_id")
    public Integer getCheckoutSheetId() {
        return checkoutSheetId;
    }

    public void setCheckoutSheetId(Integer checkoutSheetId) {
        this.checkoutSheetId = checkoutSheetId;
    }

    @Basic
    @Column(name = "father_sample_id")
    public int getFatherSampleId() {
        return fatherSampleId;
    }

    public void setFatherSampleId(int fatherSampleId) {
        this.fatherSampleId = fatherSampleId;
    }

    @Basic
    @Column(name = "concentration")
    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    @Basic
    @Column(name = "total")
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Basic
    @Column(name = "remainder")
    public double getRemainder() {
        return remainder;
    }

    public void setRemainder(double remainder) {
        this.remainder = remainder;
    }

    @Basic
    @Column(name = "return_time")
    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
    }

    @Basic
    @Column(name = "destory_time")
    public Timestamp getDestoryTime() {
        return destoryTime;
    }

    public void setDestoryTime(Timestamp destoryTime) {
        this.destoryTime = destoryTime;
    }

    @Basic
    @Column(name = "receiver")
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Basic
    @Column(name = "sample_remark")
    public String getSampleRemark() {
        return sampleRemark;
    }

    public void setSampleRemark(String sampleRemark) {
        this.sampleRemark = sampleRemark;
    }

    @Basic
    @Column(name = "sample_type")
    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectSampleEntity that = (ProjectSampleEntity) o;
        return id == that.id &&
                fatherSampleId == that.fatherSampleId &&
                Double.compare(that.total, total) == 0 &&
                Double.compare(that.remainder, remainder) == 0 &&
                Objects.equals(infoSheetId, that.infoSheetId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(spec, that.spec) &&
                Objects.equals(organ, that.organ) &&
                Objects.equals(weightOrConsistency, that.weightOrConsistency) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(processMethod, that.processMethod) &&
                Objects.equals(extractType, that.extractType) &&
                Objects.equals(note, that.note) &&
                Objects.equals(imageNote, that.imageNote) &&
                Objects.equals(storageStatus, that.storageStatus) &&
                Objects.equals(status, that.status) &&
                Objects.equals(arrivedTime, that.arrivedTime) &&
                Objects.equals(storageLocation, that.storageLocation) &&
                Objects.equals(checkoutSheetId, that.checkoutSheetId) &&
                Objects.equals(concentration, that.concentration) &&
                Objects.equals(returnTime, that.returnTime) &&
                Objects.equals(destoryTime, that.destoryTime) &&
                Objects.equals(receiver, that.receiver) &&
                Objects.equals(sampleRemark, that.sampleRemark) &&
                Objects.equals(sampleType, that.sampleType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, infoSheetId, name, spec, organ, weightOrConsistency, volume, processMethod, extractType, note, imageNote, storageStatus, status, arrivedTime, storageLocation, checkoutSheetId, fatherSampleId, concentration, total, remainder, returnTime, destoryTime, receiver, sampleRemark, sampleType);
    }

    @Basic
    @Column(name = "lab_id")
    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }
}
