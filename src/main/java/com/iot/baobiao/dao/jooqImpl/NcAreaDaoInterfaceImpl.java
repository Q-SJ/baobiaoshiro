package com.iot.baobiao.dao.jooqImpl;

import com.iot.baobiao.dao.NcAreaDaoInterface;
import com.iot.baobiao.jooq.tables.daos.NcAreaDao;
import com.iot.baobiao.jooq.tables.pojos.NcArea;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.iot.baobiao.jooq.tables.NcArea.NC_AREA;

/**
 * Created by jia on 17-1-3.
 */
@Repository
public class NcAreaDaoInterfaceImpl extends NcAreaDao implements NcAreaDaoInterface{

    @Autowired
    private DSLContext dsl;

    //由于继承了jooq自动生成的daos类，所以必须自已注入Configuration类，不然会找不到Configuration
    @Autowired
    public NcAreaDaoInterfaceImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public List<NcArea> fetchArea() {
        return dsl.select()
                .from(NC_AREA)
                .fetchInto(NcArea.class);
    }

    @Override
    public List<NcArea> fetAreaID(String city) {
        return dsl.select()
                .from(NC_AREA)
                .where(NC_AREA.NAME.contains(city))
                .fetch()
                .into(NcArea.class);
    }
}
