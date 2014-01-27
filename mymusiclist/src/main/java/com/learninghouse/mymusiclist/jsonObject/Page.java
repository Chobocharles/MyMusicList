package com.learninghouse.mymusiclist.jsonObject;

import com.google.gson.annotations.Expose;

public class Page {

    @Expose
    private String start;
    @Expose
    private Integer label;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

}