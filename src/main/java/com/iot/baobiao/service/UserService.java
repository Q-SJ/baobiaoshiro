package com.iot.baobiao.service;

import com.iot.baobiao.dao.UserDaoInterface;
import com.iot.baobiao.dao.UserSiteDaoInterface;
import com.iot.baobiao.jooq.tables.pojos.User;
import com.iot.baobiao.jooq.tables.pojos.UserSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ja on 2016/6/30.
 */

@Service
public class UserService {

    @Autowired
    UserDaoInterface userDaoInterface;

    @Autowired
    UserSiteDaoInterface userSiteDaoInterface;

    public String keyword(int user_id) {return userDaoInterface.getKeyword(user_id);}

    public void modifyKeyword(int user_id, String keyword) {
        userDaoInterface.modifyKeyword(user_id, keyword);
    }

    public User getUserInfo(int user_id) {
        User user = userDaoInterface.fetchOneById(user_id);

        return userDaoInterface.returnUser(user);
    }

    public List<UserSite> findUserSiteByID(int user_id) {
        return userSiteDaoInterface.fetchByUserId(user_id);
    }

    public List<User> findAll() {
        return userDaoInterface.fetchAll();
    }

    public void modifyInfo(User user) {
        userDaoInterface.updateInfo(user);
    }
}
