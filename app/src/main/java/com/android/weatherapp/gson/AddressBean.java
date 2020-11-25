package com.android.weatherapp.gson;

import org.json.JSONObject;

public class AddressBean {

    /**
     * （省\市\区）的名称
     */
    private String name;
    /**
     * 下一级的 Json
     */
    private JSONObject next;

    public AddressBean(String name, JSONObject next) {
        this.name = name;
        this.next = next;
    }

    public String getName() {
        return name;
    }

    public JSONObject getNext() {
        return next;
    }
}
