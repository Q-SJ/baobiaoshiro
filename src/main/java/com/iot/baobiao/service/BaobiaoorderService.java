package com.iot.baobiao.service;

import com.iot.baobiao.dao.BaobiaoorderDaoInterface;
import com.iot.baobiao.jooq.tables.pojos.Baobiaoorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jia on 2016/10/9.
 */

@Service
public class BaobiaoorderService {

    @Autowired
    private BaobiaoorderDaoInterface baobiaoOrderDaoInterface;

    public void insertOrder(final Baobiaoorder order) {
        baobiaoOrderDaoInterface.insert(order);
    }

    public Baobiaoorder findOrderByOuttradeno(final String outtradeno) {
        return baobiaoOrderDaoInterface.fetchOneByOuttradeno(outtradeno);
    }

    public void modifyTradeStatus(final int status, final String outtradeno) {
        baobiaoOrderDaoInterface.modifyStatus(status, outtradeno);
    }
}
