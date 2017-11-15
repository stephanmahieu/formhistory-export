package com.formhistory.model;

import com.formhistory.util.Utils;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("field")
public class Field {

    private String name;
    private String value;
    private long timesUsed;
    private String firstUsed;
    private String lastUsed;

    public Field(String fieldname, byte[] value, long timesUsed, long firstUsed, long lastUsed) {
        this.name = fieldname;
        this.value = Utils.encode(value);
        this.timesUsed = timesUsed;
        this.firstUsed = Utils.toDateString(firstUsed);
        this.lastUsed = Utils.toDateString(lastUsed);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public long getTimesUsed() {
        return timesUsed;
    }

    public String getFirstUsed() {
        return firstUsed;
    }

    public String getLastUsed() {
        return lastUsed;
    }

}
