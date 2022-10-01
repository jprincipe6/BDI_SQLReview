package com.sqlreview.entity;

public class Cites {
    private Integer paperIdFrom;
    private Integer paperIdDto;

    public Cites(Integer paperIdFrom, Integer paperIdDto) {
        this.paperIdFrom = paperIdFrom;
        this.paperIdDto = paperIdDto;
    }

    public Integer getPaperIdFrom() {
        return paperIdFrom;
    }

    public void setPaperIdFrom(Integer paperIdFrom) {
        this.paperIdFrom = paperIdFrom;
    }

    public Integer getPaperIdDto() {
        return paperIdDto;
    }

    public void setPaperIdDto(Integer paperIdDto) {
        this.paperIdDto = paperIdDto;
    }

    @Override
    public String toString() {
        return "Cites{" +
                "paperIdFrom=" + paperIdFrom +
                ", paperIdDto=" + paperIdDto +
                '}';
    }
}
