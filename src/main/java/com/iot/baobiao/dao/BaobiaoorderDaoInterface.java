package com.iot.baobiao.dao;

import com.iot.baobiao.jooq.tables.pojos.Baobiaoorder;

/**
 * Created by jia on 2016/10/30.
 */
public interface BaobiaoorderDaoInterface {

    void modifyStatus(int status, String outtradeno);

    Baobiaoorder fetchOneByOuttradeno(String outtradeno);

    void insert(Baobiaoorder order);
}
