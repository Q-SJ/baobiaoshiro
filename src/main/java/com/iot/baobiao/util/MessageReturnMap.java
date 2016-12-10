package com.iot.baobiao.util;

/**
 * Created by jia on 2016/11/4.
 */
public class MessageReturnMap extends ReturnMap {
    public MessageReturnMap(String status, String message) {
        super(status);
        getMap().put("message", message);
    }
}
