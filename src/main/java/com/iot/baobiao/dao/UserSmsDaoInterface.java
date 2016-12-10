package com.iot.baobiao.dao;

import com.iot.baobiao.jooq.tables.pojos.UserSms;

import java.util.Optional;

/**
 * Created by jia on 16-11-12.
 */
public interface UserSmsDaoInterface {

    void insert(UserSms userSms);

    String fetchCodeByPhonenum(String phonenum);

    UserSms fetchByPhonenum(String phonenum);

    void modifyStateByID(int smsID);
}
