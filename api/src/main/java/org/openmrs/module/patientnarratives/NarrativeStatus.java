package org.openmrs.module.patientnarratives;

import org.openmrs.Auditable;
import org.openmrs.User;

import java.util.Date;

public class NarrativeStatus implements Auditable {
    private User    changedBy;
    private String  status;
    private User    creator;
    private Date    dateChanged;
    private Date    dateCreated;
    private Integer encounterId;
    private String  uuid;

    public NarrativeStatus() {}

    public NarrativeStatus(String status, Date dateCreated, Integer encounterId) {
        this.status     = status;
        this.dateCreated = dateCreated;
        this.encounterId = encounterId;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateChanged() {
        return this.dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getEncounterId() {
        return this.encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public User getChangedBy() {
        return this.changedBy;
    }

    public Integer getId() {
        return this.encounterId;
    }

    public void setId(Integer encounterId) {
        this.encounterId = encounterId;
    }
}

