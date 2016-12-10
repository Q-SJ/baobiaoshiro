package com.iot.baobiao.dao.jooqImpl;

import com.iot.baobiao.dao.UserRolesDaoInterface;
import com.iot.baobiao.jooq.tables.daos.UserRolesDao;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.iot.baobiao.jooq.tables.UserRoles.*;

/**
 * Created by jia on 16-11-30.
 */

@Repository
public class UserRolesDaoInterfaceImpl extends UserRolesDao implements UserRolesDaoInterface {

    @Autowired
    private DSLContext dsl;

    //由于继承了jooq自动生成的daos类，所以必须自已注入Configuration类，不然会找不到Configuration
    @Autowired
    public UserRolesDaoInterfaceImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void insert(int user_id, String role) {
        dsl.insertInto(USER_ROLES)
                .set(USER_ROLES.USER_ID, user_id)
                .set(USER_ROLES.ROLE, role)
                .execute();
    }
}
