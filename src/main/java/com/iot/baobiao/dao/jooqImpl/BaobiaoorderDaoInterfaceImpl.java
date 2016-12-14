package com.iot.baobiao.dao.jooqImpl;

import com.iot.baobiao.dao.BaobiaoorderDaoInterface;
import com.iot.baobiao.jooq.tables.pojos.Baobiaoorder;
import com.iot.baobiao.jooq.tables.daos.BaobiaoorderDao;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.iot.baobiao.jooq.tables.Baobiaoorder.BAOBIAOORDER;


/**
 * Created by jia on 2016/10/9.
 */

@Repository
public class BaobiaoorderDaoInterfaceImpl extends BaobiaoorderDao implements BaobiaoorderDaoInterface {

    @Autowired
    private DSLContext dsl;

    //由于继承了jooq自动生成的daos类，所以必须自已注入Configuration类，不然会找不到Configuration
    @Autowired
    public BaobiaoorderDaoInterfaceImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void modifyStatus(int status, String outtradeno) {
        dsl.update(BAOBIAOORDER)
                .set(BAOBIAOORDER.STATUS, status)
                .where(BAOBIAOORDER.OUTTRADENO.equal(outtradeno))
                .execute();
    }

    @Override
    public Baobiaoorder fetchOneByOuttradeno(String outtradeno) {
        Record record = dsl.select()
                .from(BAOBIAOORDER)
                .where(BAOBIAOORDER.OUTTRADENO.equal(outtradeno))
                .fetchOne();

        return record == null ? null : record.into(Baobiaoorder.class);
    }

//    @Override
//    public void insert(Baobiaoorder order) {
//        dsl.insertInto(BAOBIAOORDER, BAOBIAOORDER.USERID, BAOBIAOORDER.OUTTRADENO, BAOBIAOORDER.TRADENO, BAOBIAOORDER.AMOUNT, BAOBIAOORDER.TIME, BAOBIAOORDER.STATUS)
//                .values(order.getUserid(), order.getOuttradeno(), order.getTradeno(), order.getAmount(), order.getTime(), order.getStatus())
//                .execute();
//    }
}
