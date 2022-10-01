package com.sqlreview.entity;

import java.sql.Date;

public class Submits {
    private Integer paperId;
    private Integer confId;
    private Boolean isAccepted;
    private Date date;

    public Submits(Integer paperId, Integer confId, Boolean isAccepted, Date date) {
        this.paperId = paperId;
        this.confId = confId;
        this.isAccepted = isAccepted;
        this.date = date;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getConfId() {
        return confId;
    }

    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Submits{" +
                "paperId=" + paperId +
                ", confId=" + confId +
                ", isAccepted=" + isAccepted +
                ", date=" + date +
                '}';
    }
}
