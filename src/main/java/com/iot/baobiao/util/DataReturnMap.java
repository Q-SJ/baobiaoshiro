package com.iot.baobiao.util;

/**
 * Created by jia on 2016/11/4.
 */
public class DataReturnMap extends ReturnMap {
    public DataReturnMap(String dataKey, Object data) {
        super("ok");
        addData(dataKey, data);
    }

    public void addData(String key, Object data) {
        super.addData(key, data);
    }
}
