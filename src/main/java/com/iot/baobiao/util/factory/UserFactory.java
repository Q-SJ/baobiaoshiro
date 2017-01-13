package com.iot.baobiao.util.factory;

import com.iot.baobiao.jooq.tables.pojos.User;

/**
 * Created by jia on 16-12-24.
 */
public class UserFactory {
    public static User newInstance(String username, String email, String corporation, int industry) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setCorporation(corporation);
        user.setIndustry(industry);
        return user;
    }
}
