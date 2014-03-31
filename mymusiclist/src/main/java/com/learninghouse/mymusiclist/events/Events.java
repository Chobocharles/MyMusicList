
package com.learninghouse.mymusiclist.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Events {

    private List<Event> event = new ArrayList<Event>();
    private com.learninghouse.mymusiclist.events._attr _attr;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    public com.learninghouse.mymusiclist.events._attr get_attr() {
        return _attr;
    }

    public void set_attr(com.learninghouse.mymusiclist.events._attr _attr) {
        this._attr = _attr;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
