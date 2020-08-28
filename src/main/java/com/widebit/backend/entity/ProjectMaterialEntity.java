package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_material", schema = "egenecloud", catalog = "")
public class ProjectMaterialEntity {
    private int id;
    private String supplier;
    private String name;
    private String band;
    private String norm;
    private double price;
    private int instock;
    private int purchase;
    private int outstock;
    private String number;
    private String status;
    private int threshold;
    private String unit;
    private String type;
    private int deleteRemark;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "supplier")
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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
    @Column(name = "band")
    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    @Basic
    @Column(name = "norm")
    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "instock")
    public int getInstock() {
        return instock;
    }

    public void setInstock(int instock) {
        this.instock = instock;
    }

    @Basic
    @Column(name = "purchase")
    public int getPurchase() {
        return purchase;
    }

    public void setPurchase(int purchase) {
        this.purchase = purchase;
    }

    @Basic
    @Column(name = "outstock")
    public int getOutstock() {
        return outstock;
    }

    public void setOutstock(int outstock) {
        this.outstock = outstock;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "threshold")
    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Basic
    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectMaterialEntity that = (ProjectMaterialEntity) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                instock == that.instock &&
                purchase == that.purchase &&
                outstock == that.outstock &&
                threshold == that.threshold &&
                Objects.equals(supplier, that.supplier) &&
                Objects.equals(name, that.name) &&
                Objects.equals(band, that.band) &&
                Objects.equals(norm, that.norm) &&
                Objects.equals(number, that.number) &&
                Objects.equals(status, that.status) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplier, name, band, norm, price, instock, purchase, outstock, number, status, threshold, unit);
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
    @Column(name = "delete_remark")
    public int getDeleteRemark() {
        return deleteRemark;
    }

    public void setDeleteRemark(int deleteRemark) {
        this.deleteRemark = deleteRemark;
    }
}
