package com.iot.baobiao.service;

import com.iot.baobiao.dao.PropertiesDao;
import com.iot.baobiao.pojo.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jia on 17-1-11.
 */
@Service
public class PropertiesService {

    @Autowired
    PropertiesDao propertiesDao;

    public Discount getDiscount() {
        return propertiesDao.getDiscount();
    }

    public void updateDiscount(Discount discount) {
        propertiesDao.updateDiscount(discount);
    }

    public void updateTrialDays(int day) {
        propertiesDao.updateTrialDays(day);
    }
}
