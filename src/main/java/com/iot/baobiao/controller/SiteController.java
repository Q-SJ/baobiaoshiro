package com.iot.baobiao.controller;

import com.iot.baobiao.exception.NoKeywordsException;
import com.iot.baobiao.jooq.tables.pojos.UserSite;
import com.iot.baobiao.service.DataService;
import com.iot.baobiao.service.ManageSiteService;
import com.iot.baobiao.util.DataReturnMap;
import com.iot.baobiao.util.ErrorReturnMap;
import com.iot.baobiao.util.OKReturnMap;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by ja on 2016/6/22.
 */

//负责网站添加、删除及查询的请求
@RestController
@RequestMapping("/site")
public class SiteController {

    @Autowired
    ManageSiteService manageSiteService;

    @Autowired
    private DataService dataService;

    private static Logger log = Logger.getLogger(SiteController.class);

    @PostMapping("/add")
    public Map<String, Object> add_site(@RequestParam String url,
                                        @RequestParam(required = false) String sitename,
                                        HttpSession session) {
        int user_id = (Integer) session.getAttribute("user_id");
        Map<String, String> args = new HashMap<String, String>();
        if (!(url.startsWith("http://") || url.startsWith("https://"))) url = "http://" + url;
        String domain;
        try {
            URL u = new URL(url);
            domain = u.getHost();
        } catch (MalformedURLException e) {
            return new ErrorReturnMap("输入的网址格式错误！").getMap();
        }

        args.put("url", url);
        args.put("domain", domain);
        args.put("sitename", sitename);
        int site_id = manageSiteService.addUserSite(user_id, args);
        return new DataReturnMap("site_id", site_id).getMap();
    }

    @PostMapping("/delete")
    public Map<String, Object> delete_site(HttpSession session,
                                           @RequestParam int site_id) {
        int user_id = (Integer) session.getAttribute("user_id");
        manageSiteService.deleteUserSite(user_id, site_id);
        return new OKReturnMap("删除网站成功！").getMap();
    }

    @RequestMapping("/query")
    public Map<String, Object> query_site(HttpSession session) {
        int user_id = (Integer) session.getAttribute("user_id");
        List<UserSite> list = manageSiteService.queryUserSite(user_id);
        return new DataReturnMap("sites", list).getMap();
    }

    @PostMapping("/data")
    public Map<String, Object> fetch_data(HttpSession session,
                                          @RequestParam(required = false, defaultValue = "-1") int data_id,
                                          @RequestParam String words,
                                          @RequestParam(required = false, defaultValue = "true") boolean own,
                                          @RequestParam(required = false) String fromTime) {
        int user_id = (Integer) session.getAttribute("user_id");

        List<Integer> ids = manageSiteService.queryUserSiteID(user_id);

        if (words == null) throw new NoKeywordsException();
        List<String> wordList = Arrays.asList(words.split(","));

//        Long timestamp = fromTime == null ? -1 : Long.parseLong(fromTime);
//
//        DateTime time = timestamp == -1 ? null : new DateTime(timestamp);

        return new DataReturnMap("data", dataService.fetchData(ids, data_id, wordList, fromTime, own)).getMap();
    }

    @RequestMapping("/domains")
    public Map<String, Object> fetchDomainList() {
        return new DataReturnMap("domains", manageSiteService.queryDomainList()).getMap();
    }

}
