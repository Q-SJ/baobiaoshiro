package com.iot.baobiao.controller;

import com.iot.baobiao.exception.DuplicateSiteException;
import com.iot.baobiao.exception.NoKeywordsException;
import com.iot.baobiao.util.ErrorReturnMap;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ja on 2016/6/22.
 */

@ControllerAdvice
//@RestController
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MalformedURLException.class)
    public Map<String, Object> malformedURL() {
        return new ErrorReturnMap("输入网址的格式错误!").getMap();
    }

    @ResponseBody
    @ExceptionHandler(DuplicateSiteException.class)
    public Map<String, Object> duplicateSite() {
        return new ErrorReturnMap("您已添加过此网站！").getMap();
    }

    @ResponseBody
    @ExceptionHandler(NoKeywordsException.class)
    public Map<String, Object> noKeywords() {
        return new ErrorReturnMap("没有添加关键字！").getMap();
    }

    @ResponseBody
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public Map<String, Object> emptyData() {
        return new ErrorReturnMap("您请求的数据不存在！").getMap();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorized() {
        return "redirect:/unauthorized";
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public Map<String, Object> authenticationFailed() {
        return new ErrorReturnMap("登录失败，用户名或密码错误！").getMap();
    }
}
