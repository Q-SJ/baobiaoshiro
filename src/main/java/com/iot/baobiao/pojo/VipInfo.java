package com.iot.baobiao.pojo;

import java.time.LocalDateTime;

/**
 * Created by jia on 17-1-11.
 */
public class VipInfo {
    private int days;

    public VipInfo() {
    }

    public VipInfo(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
