package com.iot.baobiao.dao;

import com.iot.baobiao.jooq.tables.pojos.NcArea;

import java.util.List;

/**
 * Created by jia on 17-1-3.
 */
public interface NcAreaDaoInterface {
    List<NcArea> fetchArea();

    List<NcArea> fetAreaID(String city);
}
