package com.sqlreview.entity;

public class Writes {
    private Integer authorId;
    private Integer paperId;

    public Writes(Integer authorId, Integer paperId) {
        this.authorId = authorId;
        this.paperId = paperId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    @Override
    public String toString() {
        return "Writes{" +
                "authorId=" + authorId +
                ", paperId=" + paperId +
                '}';
    }
}
