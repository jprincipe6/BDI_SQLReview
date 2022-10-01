package com.sqlreview.api;

public class Paper {
    private Integer paperId;
    private String title;
    private String abStract;

    public Paper(Integer paperId, String title, String abStract) {
        this.paperId = paperId;
        this.title = title;
        this.abStract = abStract;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbStract() {
        return abStract;
    }

    public void setAbStract(String abStract) {
        this.abStract = abStract;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "paperId=" + paperId +
                ", title='" + title + '\'' +
                ", abStract='" + abStract + '\'' +
                '}';
    }
}
