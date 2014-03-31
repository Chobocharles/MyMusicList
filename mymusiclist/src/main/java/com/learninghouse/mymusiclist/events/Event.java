
package com.learninghouse.mymusiclist.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private List<Object> links = new ArrayList<Object>();
    private Integer id;
    private Stats stats;
    private String title;
    private Double score;
    private Boolean date_tbd;
    private String type;
    private String datetime_local;
    private String visible_until_utc;
    private Boolean time_tbd;
    private List<Taxonomy> taxonomies = new ArrayList<Taxonomy>();
    private List<Performer> performers = new ArrayList<Performer>();
    private String url;
    private Venue venue;
    private String short_title;
    private String datetime_utc;
    private Boolean general_admission;
    private Boolean datetime_tbd;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Object> getLinks() {
        return links;
    }

    public void setLinks(List<Object> links) {
        this.links = links;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean getDate_tbd() {
        return date_tbd;
    }

    public void setDate_tbd(Boolean date_tbd) {
        this.date_tbd = date_tbd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatetime_local() {
        return datetime_local;
    }

    public void setDatetime_local(String datetime_local) {
        this.datetime_local = datetime_local;
    }

    public String getVisible_until_utc() {
        return visible_until_utc;
    }

    public void setVisible_until_utc(String visible_until_utc) {
        this.visible_until_utc = visible_until_utc;
    }

    public Boolean getTime_tbd() {
        return time_tbd;
    }

    public void setTime_tbd(Boolean time_tbd) {
        this.time_tbd = time_tbd;
    }

    public List<Taxonomy> getTaxonomies() {
        return taxonomies;
    }

    public void setTaxonomies(List<Taxonomy> taxonomies) {
        this.taxonomies = taxonomies;
    }

    public List<Performer> getPerformers() {
        return performers;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getDatetime_utc() {
        return datetime_utc;
    }

    public void setDatetime_utc(String datetime_utc) {
        this.datetime_utc = datetime_utc;
    }

    public Boolean getGeneral_admission() {
        return general_admission;
    }

    public void setGeneral_admission(Boolean general_admission) {
        this.general_admission = general_admission;
    }

    public Boolean getDatetime_tbd() {
        return datetime_tbd;
    }

    public void setDatetime_tbd(Boolean datetime_tbd) {
        this.datetime_tbd = datetime_tbd;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
