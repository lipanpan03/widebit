package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_purchaselist", schema = "egenecloud", catalog = "")
public class ProjectPurchaselistEntity {
    private int id;
    private int materialId;
    private int purchaseCount;
    private String stockStatus;
    private String invoiceStatus;
    private double payment;
    private int sheetId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "material_id")
    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    @Basic
    @Column(name = "purchase_count")
    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    @Basic
    @Column(name = "stock_status")
    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
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
    @Column(name = "payment")
    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Basic
    @Column(name = "sheet_id")
    public int getSheetId() {
        return sheetId;
    }

    public void setSheetId(int sheetId) {
        this.sheetId = sheetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectPurchaselistEntity that = (ProjectPurchaselistEntity) o;
        return id == that.id &&
                materialId == that.materialId &&
                purchaseCount == that.purchaseCount &&
                Double.compare(that.payment, payment) == 0 &&
                sheetId == that.sheetId &&
                Objects.equals(stockStatus, that.stockStatus) &&
                Objects.equals(invoiceStatus, that.invoiceStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, materialId, purchaseCount, stockStatus, invoiceStatus, payment, sheetId);
    }
}
