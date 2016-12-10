package com.iot.baobiao.dao.jooqImpl;

import com.iot.baobiao.dao.UserSmsDaoInterface;
import com.iot.baobiao.jooq.tables.UserSms;
import com.iot.baobiao.jooq.tables.daos.UserSmsDao;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.iot.baobiao.jooq.tables.UserSms.USER_SMS;

/**
 * Created by jia on 16-11-12.
 */

@Repository
public class UserSmsDaoInterfaceImpl extends UserSmsDao implements UserSmsDaoInterface {

    @Autowired
    DSLContext dsl;

    //由于继承了jooq自动生成的daos类，所以必须自已注入Configuration类，不然会找不到Configuration
    @Autowired
    public UserSmsDaoInterfaceImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public String fetchCodeByPhonenum(String phonenum) {
        Record record = dsl.select(USER_SMS.SMSCODE)
                .from(USER_SMS)
                .where(USER_SMS.PHONENUM.equal(phonenum))
                .orderBy(USER_SMS.SEND_TIME.desc())
                .fetchAny();
        return record == null ? null : record.into(String.class);
    }

    @Override
    public com.iot.baobiao.jooq.tables.pojos.UserSms fetchByPhonenum(String phonenum) {
        Record record = dsl.select()
                .from(USER_SMS)
                .where(USER_SMS.PHONENUM.equal(phonenum))
                .orderBy(USER_SMS.SEND_TIME.desc())
                .fetchAny();
        return record == null ? null : record.into(com.iot.baobiao.jooq.tables.pojos.UserSms.class);
    }

    @Override
    public void modifyStateByID(int smsID) {
        dsl.update(USER_SMS)
                .set(USER_SMS.STATE, 0)
                .where(USER_SMS.ID.equal(smsID))
                .execute();
    }

//    @Override
//    public void insert(com.iot.baobiao.jooq.tables.pojos.UserSms userSms) {
//        dsl.insertInto(USER_SMS, USER_SMS.PHONENUM, USER_SMS.SMSCODE, USER_SMS.SEND_TIME, USER_SMS.STATE)
//                .values(userSms.getPhonenum(), userSms.getSmscode(), userSms.getSendTime(), userSms.getState())
//                .execute();
//    }
}
