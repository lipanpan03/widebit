package com.widebit.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_resultnote", schema = "egenecloud", catalog = "")
public class ProjectResultnoteEntity {
    private int id;
    private String labId;
    private String filePath;
    private String resultNote;

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
    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "result_note")
    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectResultnoteEntity that = (ProjectResultnoteEntity) o;
        return id == that.id &&
                Objects.equals(labId, that.labId) &&
                Objects.equals(filePath, that.filePath) &&
                Objects.equals(resultNote, that.resultNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, labId, filePath, resultNote);
    }
}
