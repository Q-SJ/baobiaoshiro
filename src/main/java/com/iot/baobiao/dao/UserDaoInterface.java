package com.iot.baobiao.dao;

import com.iot.baobiao.jooq.tables.pojos.User;

import java.util.List;
import java.util.Set;

/**
 * Created by jia on 2016/10/30.
 */
public interface UserDaoInterface {

    String getKeyword(int user_id);

    void modifyKeyword(int user_id, String keyword);

    User fetchOneByPhonenum(String phonenum);

    User returnUser(User user);

    int insertUserAndGetID(User user);

    int fetchIDByPhonenum(String phonenum);

    Set<String> fetchRoleByPhonenum(String phonenum);

    Set<String> fetchPermissionByPhonenum(String phonenum);

    void changePassword(String phonenum, String newPassword, String salt);

    User fetchOneById(Integer user_id);

    void modifyLoginTime(int user_id);

    List<User> fetchAll();

    void updateInfo(User user);

    String fetchPhonenumByID(int user_id);
}
