package com.sqlreview.entity;

public class Conference {
    private Integer confId;
    private String name;
    private Integer ranking;

    public Conference(Integer confId, String name, Integer ranking) {
        this.confId = confId;
        this.name = name;
        this.ranking = ranking;
    }

    public Integer getConfId() {
        return confId;
    }

    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "confId=" + confId +
                ", name='" + name + '\'' +
                ", ranking=" + ranking +
                '}';
    }
}
