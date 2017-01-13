package com.iot.baobiao.dao;

import com.iot.baobiao.pojo.Discount;
import com.iot.baobiao.pojo.VipInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jia on 17-1-11.
 */

@Repository
public class PropertiesDao {

    @Autowired
    Discount discount;

    @Autowired
    VipInfo info;

    public Discount getDiscount() {
        return discount;
    }

    public void updateDiscount(Discount newDiscount) {
        discount = newDiscount;
    }

    public int getTrialDays() {
        return info.getDays();
    }

    public void updateTrialDays(int day) {
        info.setDays(day);
    }
}
