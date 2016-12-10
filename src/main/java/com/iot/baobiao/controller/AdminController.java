package com.iot.baobiao.controller;

import com.iot.baobiao.service.DataService;
import com.iot.baobiao.service.LoginService;
import com.iot.baobiao.service.ManageSiteService;
import com.iot.baobiao.service.UserService;
import com.iot.baobiao.util.DataReturnMap;
import com.iot.baobiao.util.ErrorReturnMap;
import com.iot.baobiao.util.OKReturnMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by jia on 2016/11/4.
 */
@RestController
@RequestMapping("/admin")
@RequiresRoles("admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ManageSiteService manageSiteService;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    DataService dataService;

    @PostMapping("/grant")
    @RequiresRoles("superadmin")
    public Map<String, Object> grant(@RequestParam int user_id, @RequestParam String role, HttpSession session) {
        logger.info("管理员" + session.getAttribute("phonenum") + "正在授予用户" + user_id + "管理员权限...");
        loginService.grant(user_id, role);
        return new OKReturnMap("已授予用户" + user_id + role + "角色！").getMap();
    }

    //以下为用户管理
    @RequestMapping("/users")
    public Map<String, Object> findAllUsers() {
        return new DataReturnMap("users", userService.findAll()).getMap();
    }

    @PostMapping("/userFindByID")
    public Map<String, Object> findUserByID(@RequestParam("userID") int user_id) {
        return new DataReturnMap("user", userService.getUserInfo(user_id)).getMap();
    }

    @PostMapping("/userSiteFindByID")
    public Map<String, Object> findUserSiteByID(@RequestParam("userID") int user_id) {
        return new DataReturnMap("site", userService.findUserSiteByID(user_id)).getMap();
    }

    //以下为网站管理
    @RequestMapping("/sites")
    public Map<String, Object> findAllSites() {
        return new DataReturnMap("sites", manageSiteService.findAll()).getMap();
    }

    @PostMapping("/deleteSiteByUrl")
    public Map<String, Object> deleteSiteByUrl(@RequestParam String url) {

        Subject currentUser = SecurityUtils.getSubject();

        String message;
        if (!(url.startsWith("http://") || url.startsWith("https://"))) url = "http://" + url;

        try {
            URL u = new URL(url);
            String domain = u.getHost();
            message = manageSiteService.deleteSiteByDomain(domain);
        } catch (MalformedURLException e) {
            return new ErrorReturnMap("输入网址的格式错误!").getMap();
        }

        logger.info(currentUser.getPrincipal() + "删除网站 " + url + " 的结果是：" + message);
        return new OKReturnMap(message).getMap();
    }


    @PostMapping("/siteFindByName")
    public Map<String, Object> findSiteByProvience(@RequestParam String name) {
//        if (province == null && name == null) return new ErrorReturnMap("参数错误！");
        return new DataReturnMap("sites", manageSiteService.findSiteByName(name)).getMap();
    }

    @PostMapping("/siteFindByURL")
    public Map<String, Object> findSiteByURL(@RequestParam String url) {
        String domain;
        try {
            URL u = new URL(url);
            domain = u.getHost();
        } catch (MalformedURLException e) {
            return new ErrorReturnMap("输入的网址格式错误！").getMap();
        }
        return new DataReturnMap("site", manageSiteService.findSiteByDomain(domain)).getMap();
    }

    @PostMapping("/siteInfo")
    public Map<String, Object> findSiteInfo(@RequestParam int site_id,
                                            @RequestParam(required = false, defaultValue = "-1") int data_id) {
        return new DataReturnMap("data", dataService.findDataBySite(site_id, data_id)).getMap();
    }
}
