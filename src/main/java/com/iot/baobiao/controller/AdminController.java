package com.iot.baobiao.controller;

import com.iot.baobiao.jooq.tables.pojos.Site;
import com.iot.baobiao.service.*;
import com.iot.baobiao.util.DataReturnMap;
import com.iot.baobiao.util.ErrorReturnMap;
import com.iot.baobiao.util.OKReturnMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    SiteService siteService;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    DataService dataService;

    @Autowired
    PropertiesService propertiesService;

    @PostMapping("/grant")
    @RequiresRoles("superadmin")
    public Map<String, Object> grant(@RequestParam int user_id, @RequestParam String role, HttpSession session) {
        logger.info("管理员" + session.getAttribute("phonenum") + "正在授予用户" + user_id + "管理员权限...");
        loginService.grant(user_id, role);
        return new OKReturnMap("已授予用户" + user_id + role + "角色！").getMap();
    }

    //设置用户注册后试用时间
    public Map<String, Object> setTrialDays(@RequestParam int day, HttpSession session) {
        propertiesService.updateTrialDays(day);
        logger.info("管理员" + session.getAttribute("phonenum") + "把试用时间设置成了" + day + "天！");
        return new OKReturnMap("修改试用欺限成功！").getMap();
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
        return new DataReturnMap("sites", siteService.findAll()).getMap();
    }

    @PostMapping("/siteModify")
    public Map<String, Object> modifySite(@RequestBody Site site) {
        siteService.modifySite(site);
        return new OKReturnMap("修改成功！").getMap();
    }

    @PostMapping("/siteDelete")
    public Map<String, Object> deleteSite(@RequestParam(required = false) String url,
                                               @RequestParam(required = false, defaultValue = "-1") int site_id) {
        if (url == null && site_id == -1) {
            return new ErrorReturnMap("输入参数有误！").getMap();
        }

        Subject currentUser = SecurityUtils.getSubject();
        if (site_id != -1) url = siteService.findUrlByID(site_id);
        String message;
        if (!(url.startsWith("http://") || url.startsWith("https://"))) url = "http://" + url;

        try {
            URL u = new URL(url);
            String domain = u.getHost();
            message = siteService.deleteSiteByDomain(domain);
        } catch (MalformedURLException e) {
            return new ErrorReturnMap("输入网址的格式错误!").getMap();
        }

        logger.info(currentUser.getPrincipal() + "删除网站 " + url + " 的结果是：" + message);
        return new OKReturnMap(message).getMap();
    }

    @PostMapping("/siteFindByName")
    public Map<String, Object> findSiteByProvience(@RequestParam String name) {
//        if (province == null && name == null) return new ErrorReturnMap("参数错误！");
        return new DataReturnMap("sites", siteService.findSiteByName(name)).getMap();
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
        return new DataReturnMap("site", siteService.findSiteByDomain(domain)).getMap();
    }

    @PostMapping("/siteInfo")
    public Map<String, Object> findSiteInfo(@RequestParam int site_id,
                                            @RequestParam(required = false, defaultValue = "-1") int data_id) {
        return new DataReturnMap("data", dataService.findDataBySite(site_id, data_id)).getMap();
    }
}
