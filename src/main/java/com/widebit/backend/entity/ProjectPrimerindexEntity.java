package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_primerindex", schema = "egenecloud", catalog = "")
public class ProjectPrimerindexEntity {
    private int id;
    private String primerIndexId;
    private String primerIndexSequence;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "primer_index_id")
    public String getPrimerIndexId() {
        return primerIndexId;
    }

    public void setPrimerIndexId(String primerIndexId) {
        this.primerIndexId = primerIndexId;
    }

    @Basic
    @Column(name = "primer_index_sequence")
    public String getPrimerIndexSequence() {
        return primerIndexSequence;
    }

    public void setPrimerIndexSequence(String primerIndexSequence) {
        this.primerIndexSequence = primerIndexSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectPrimerindexEntity that = (ProjectPrimerindexEntity) o;
        return id == that.id &&
                Objects.equals(primerIndexId, that.primerIndexId) &&
                Objects.equals(primerIndexSequence, that.primerIndexSequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, primerIndexId, primerIndexSequence);
    }
}
