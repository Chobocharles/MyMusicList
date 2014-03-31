
package com.learninghouse.mymusiclist.events;

import java.util.HashMap;
import java.util.Map;

public class _attr {

    private String artist;
    private String festivalsonly;
    private String page;
    private String perPage;
    private String totalPages;
    private String total;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFestivalsonly() {
        return festivalsonly;
    }

    public void setFestivalsonly(String festivalsonly) {
        this.festivalsonly = festivalsonly;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPerPage() {
        return perPage;
    }

    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
