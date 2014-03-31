
package com.learninghouse.mymusiclist.events;

import java.util.HashMap;
import java.util.Map;

public class Image {

    private String _text;
    private String size;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
