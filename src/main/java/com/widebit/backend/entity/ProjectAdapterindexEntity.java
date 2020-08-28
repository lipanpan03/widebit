package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_adapterindex", schema = "egenecloud", catalog = "")
public class ProjectAdapterindexEntity {
    private int id;
    private String adapterIndexId;
    private String adapterIndexSequence;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "adapter_index_id")
    public String getAdapterIndexId() {
        return adapterIndexId;
    }

    public void setAdapterIndexId(String adapterIndexId) {
        this.adapterIndexId = adapterIndexId;
    }

    @Basic
    @Column(name = "adapter_index_sequence")
    public String getAdapterIndexSequence() {
        return adapterIndexSequence;
    }

    public void setAdapterIndexSequence(String adapterIndexSequence) {
        this.adapterIndexSequence = adapterIndexSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectAdapterindexEntity that = (ProjectAdapterindexEntity) o;
        return id == that.id &&
                Objects.equals(adapterIndexId, that.adapterIndexId) &&
                Objects.equals(adapterIndexSequence, that.adapterIndexSequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adapterIndexId, adapterIndexSequence);
    }
}
