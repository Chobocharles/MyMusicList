
package com.learninghouse.mymusiclist.events;

import java.util.HashMap;
import java.util.Map;

public class Location {

    private Geo_point geo_point;
    private String city;
    private String country;
    private String street;
    private String postalcode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Geo_point getGeo_point() {
        return geo_point;
    }

    public void setGeo_point(Geo_point geo_point) {
        this.geo_point = geo_point;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
