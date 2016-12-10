package com.iot.baobiao.service;

import java.util.Map;

/**
 * Created by jia on 2016/11/4.
 */
public interface LoginService {

    Map<String, Object> sendMessage(String phonenum);

    Map<String, Object> signup(String phonenum, String password, String code);

    Map<String, Object> login(String phonenum, String password, boolean askForSites);

    void logout();

    Map<String,Object> changePassword(String phonenum, String oldPassword, String newPassword);

    Map<String,Object> forgetPassword(String phonenum, String password, String code);

    void grant(int user_id, String role);
}
