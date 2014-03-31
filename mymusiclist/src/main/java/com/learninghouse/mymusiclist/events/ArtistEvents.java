
package com.learninghouse.mymusiclist.events;

import java.util.HashMap;
import java.util.Map;

public class ArtistEvents {

    private Events events;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
