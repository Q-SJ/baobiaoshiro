package com.iot.baobiao.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jia on 2016/11/3.
 */
public class ReturnMap {

    private Map<String, Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public ReturnMap(String status) {
        map = new HashMap<String, Object>();
        map.put("status", status);
    }

    protected void addData(String key, Object object) {
        map.put(key, object);
    }
}
