
package com.learninghouse.mymusiclist.events;

import java.util.HashMap;
import java.util.Map;

public class Artists {

    private String artist;
    private String headliner;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getHeadliner() {
        return headliner;
    }

    public void setHeadliner(String headliner) {
        this.headliner = headliner;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
