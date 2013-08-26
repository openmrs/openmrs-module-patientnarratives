package org.openmrs.module.patientnarratives.api;

import org.openmrs.Auditable;
import org.openmrs.User;

import java.util.Date;

public class NarrativeComments implements Auditable {
    private byte[]  attachment;
    private User    changedBy;
    private String  comment;
    private User    creator;
    private Date    dateChanged;
    private Date    dateCreated;
    private Integer narrativesCommentId;
    private Integer encounterId;
    private String  uuid;

    public NarrativeComments() {}

    public NarrativeComments(String comment, Date dateCreated, Integer encounterId) {
        this.comment     = comment;
        this.dateCreated = dateCreated;
        this.encounterId = encounterId;
    }

    public NarrativeComments(String comment, byte[] attachment, Date dateCreated, Integer encounterId) {
        this.dateCreated = dateCreated;
        this.comment     = comment;
        this.attachment  = attachment;
        this.encounterId = encounterId;
    }

    public Integer getNarrativesCommentId() {
        return this.narrativesCommentId;
    }

    public void setNarrativesCommentId(Integer narrativesCommentId) {
        this.narrativesCommentId = narrativesCommentId;
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

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public byte[] getAttachment() {
        return this.attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
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

