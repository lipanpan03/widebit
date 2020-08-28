package com.widebit.backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "project_event", schema = "egenecloud", catalog = "")
public class ProjectEventEntity {
    private int id;
    private String labId;
    private String userId;
    private String eventName;
    private Timestamp startTime;
    private String nextEvent;

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
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "event_name")
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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
    @Column(name = "next_event")
    public String getNextEvent() {
        return nextEvent;
    }

    public void setNextEvent(String nextEvent) {
        this.nextEvent = nextEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEventEntity that = (ProjectEventEntity) o;
        return id == that.id &&
                Objects.equals(labId, that.labId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(eventName, that.eventName) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(nextEvent, that.nextEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, labId, userId, eventName, startTime, nextEvent);
    }
}
