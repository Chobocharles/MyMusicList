
package com.learninghouse.mymusiclist.events;

import java.util.HashMap;
import java.util.Map;

public class Geo_point {

    private String geo_lat;
    private String geo_long;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getGeo_lat() {
        return geo_lat;
    }

    public void setGeo_lat(String geo_lat) {
        this.geo_lat = geo_lat;
    }

    public String getGeo_long() {
        return geo_long;
    }

    public void setGeo_long(String geo_long) {
        this.geo_long = geo_long;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
