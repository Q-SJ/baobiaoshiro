package com.iot.baobiao.dao.jooqImpl;

import com.iot.baobiao.dao.UserDaoInterface;
import com.iot.baobiao.jooq.tables.daos.UserDao;
import com.iot.baobiao.jooq.tables.pojos.User;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.iot.baobiao.jooq.tables.RolesPermissions.*;
import static com.iot.baobiao.jooq.tables.User.*;
import static com.iot.baobiao.jooq.tables.UserRoles.*;


/**
 * Created by ja on 2016/6/22.
 */

@Repository
public class UserDaoInterfaceImpl extends UserDao implements UserDaoInterface {

    @Autowired
    private DSLContext dsl;

    //由于继承了jooq自动生成的daos类，所以必须自已注入Configuration类，不然会找不到Configuration
    @Autowired
    public UserDaoInterfaceImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public String getKeyword(int user_id) {
        Record record = dsl.select(USER.KEYWORD)
                .from(USER)
                .where(USER.ID.equal(user_id))
                .fetchOne();
        return record == null ? "" : record.into(String.class);
    }

    @Override
    public void modifyKeyword(int user_id, String keyword) {
        dsl.update(USER)
                .set(USER.KEYWORD, keyword)
                .where(USER.ID.equal(user_id))
                .execute();
    }

    @Override
    public com.iot.baobiao.jooq.tables.pojos.User fetchOneByPhonenum(String phonenum) {
        Record record = dsl.select()
                .from(USER)
                .where(USER.PHONENUM.equal(phonenum))
                .fetchOne();
        return record == null ? null : record.into(com.iot.baobiao.jooq.tables.pojos.User.class);
    }

    @Override
    public User returnUser(User user) {
        if (user == null) return null;
        user.setSalt("");
        user.setPassword("");
//        user.setId(null);
        return user;
    }

    @Override
    public int insertUserAndGetID(com.iot.baobiao.jooq.tables.pojos.User user) {
        return dsl.insertInto(USER, USER.PHONENUM, USER.PASSWORD, USER.SALT, USER.LOGIN_TIME)
                .values(user.getPhonenum(), user.getPassword(), user.getSalt(), user.getLoginTime())
                .returning(USER.ID)
                .fetchOne()
                .getValue(USER.ID);
    }

    @Override
    public int fetchIDByPhonenum(String phonenum) {
        return dsl.select(USER.ID)
                .from(USER)
                .where(USER.PHONENUM.equal(phonenum))
                .fetchOne()
                .into(Integer.class);
    }

    @Override
    public Set<String> fetchRoleByPhonenum(String phonenum) {
        return new HashSet<String>(dsl.select(USER_ROLES.ROLE)
                .from(USER_ROLES, USER)
                .where(USER.PHONENUM.equal(phonenum)
                        .and(USER.ID.equal(USER_ROLES.USER_ID)))
                .fetch()
                .into(String.class));
    }

    @Override
    public Set<String> fetchPermissionByPhonenum(String phonenum) {
        return new HashSet<String>(dsl.select(ROLES_PERMISSIONS.PERMISSION)
                .from(ROLES_PERMISSIONS, USER_ROLES, USER)
                .where(USER.PHONENUM.equal(phonenum)
                        .and(USER.ID.equal(USER_ROLES.USER_ID))
                        .and(USER_ROLES.ROLE.equal(ROLES_PERMISSIONS.ROLE))
                ).fetch()
                .into(String.class));
    }

    @Override
    public void changePassword(String phonenum, String newPassword, String salt) {
        dsl.update(USER)
                .set(USER.PASSWORD, newPassword)
                .set(USER.SALT, salt)
                .where(USER.PHONENUM.equal(phonenum))
                .execute();
    }

    @Override
    public void modifyLoginTime(int user_id) {
        dsl.update(USER)
                .set(USER.LOGIN_TIME, LocalDateTime.now())
                .where(USER.ID.equal(user_id))
                .execute();
    }

    @Override
    public List<User> fetchAll() {
        return dsl.select()
                .from(USER)
                .fetch()
                .into(User.class);
    }

    @Override
    public void updateInfo(User user) {
        dsl.update(USER)
                .set(USER.USERNAME, user.getUsername())
                .set(USER.EMAIL, user.getEmail())
                .set(USER.CORPORATION, user.getCorporation())
                .set(USER.INDUSTRY, user.getIndustry())
                .where(USER.ID.equal(user.getId()))
                .execute();
    }

    @Override
    public String fetchPhonenumByID(int user_id) {
        return dsl.select(USER.PHONENUM)
                .from(USER)
                .where(USER.ID.equal(user_id))
                .fetchAny()
                .into(String.class);
    }
}
