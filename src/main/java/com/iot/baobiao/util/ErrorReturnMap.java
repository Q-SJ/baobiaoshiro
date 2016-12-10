package com.iot.baobiao.util;

/**
 * Created by jia on 2016/11/3.
 */
public class ErrorReturnMap extends MessageReturnMap {
    public ErrorReturnMap(String message) {
        super("error", message);
    }
}
