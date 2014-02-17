
package com.learninghouse.mymusiclist.events;

import java.util.HashMap;
import java.util.Map;

public class Stats {

    private Integer listing_count;
    private Double average_price;
    private Double lowest_price;
    private Double highest_price;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getListing_count() {
        return listing_count;
    }

    public void setListing_count(Integer listing_count) {
        this.listing_count = listing_count;
    }

    public Double getAverage_price() {
        return average_price;
    }

    public void setAverage_price(Double average_price) {
        this.average_price = average_price;
    }

    public Double getLowest_price() {
        return lowest_price;
    }

    public void setLowest_price(Double lowest_price) {
        this.lowest_price = lowest_price;
    }

    public Double getHighest_price() {
        return highest_price;
    }

    public void setHighest_price(Double highest_price) {
        this.highest_price = highest_price;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
