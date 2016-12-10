package com.iot.baobiao.controller;

import com.iot.baobiao.service.LoginService;
import com.iot.baobiao.service.LoginServiceImpl;
import com.iot.baobiao.util.ErrorReturnMap;
import com.iot.baobiao.util.OKReturnMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by jia on 2016/11/2.
 */
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping("/unauthenticated")
    public Map<String, Object> unauthenticated() {
        return new ErrorReturnMap("未登录！").getMap();
    }

    @RequestMapping("/unauthorized")
    public Map<String, Object> unauthorized() {
        return new ErrorReturnMap("无访问权限！").getMap();
    }

    @RequestMapping("/verification")
    public Map<String, Object> verificationCode(@RequestParam String phonenum) {
        return loginService.sendMessage(phonenum);
    }

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestParam String phonenum,
                                      @RequestParam String password,
                                      @RequestParam String code) {
        return loginService.signup(phonenum, password, code);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String phonenum,
                                     @RequestParam String password,
                                     @RequestParam(required = false) boolean askForSites) {
        return loginService.login(phonenum, password, askForSites);
    }

    @RequestMapping("/logout")
    public Map<String, Object> logout() {
        loginService.logout();
        return new OKReturnMap("您已退出登录！").getMap();
    }

    @PostMapping("/changePassword")
    public Map<String, Object> changePassword(HttpSession session,
                                              @RequestParam String oldPassword,
                                              @RequestParam String newPassword) {
        String phonenum = (String) session.getAttribute("phonenum");
        if (StringUtils.isBlank(phonenum)) return new ErrorReturnMap("session中没有手机号信息！").getMap();
        return loginService.changePassword(phonenum, oldPassword, newPassword);
    }

    @PostMapping("/forgetPassword")
    public Map<String, Object> forgetPassword(@RequestParam String phonenum,
                                              @RequestParam String password,
                                              @RequestParam String code) {
        return loginService.forgetPassword(phonenum, password, code);
    }
}
