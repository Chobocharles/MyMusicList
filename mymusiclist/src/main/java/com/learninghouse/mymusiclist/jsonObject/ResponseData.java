package com.learninghouse.mymusiclist.jsonObject;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class ResponseData {

    @Expose
    private List<Result> results = new ArrayList<Result>();
    @Expose
    private Cursor cursor;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

}