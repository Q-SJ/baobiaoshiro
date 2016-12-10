package com.iot.baobiao.controller;

import com.iot.baobiao.jooq.tables.pojos.User;
import com.iot.baobiao.service.UserService;
import com.iot.baobiao.util.DataReturnMap;
import com.iot.baobiao.util.OKReturnMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by ja on 2016/6/30.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //修改用户的关键字
    @PostMapping("/keywords")
    public Map<String, Object> modifyKeyword(HttpSession session, @RequestParam String keyword) {
        int user_id = (Integer) session.getAttribute("user_id");
        userService.modifyKeyword(user_id, keyword);
        return new OKReturnMap("修改关键字成功！").getMap();
    }

    @RequestMapping("/getKeywords")
    public Map<String, Object> keyword(HttpSession session) {
        int user_id = (Integer) session.getAttribute("user_id");
        return new DataReturnMap("keywords", userService.keyword(user_id)).getMap();
    }

    @RequestMapping("/getInfo")
    public Map<String, Object> info(HttpSession session) {
        int user_id = (Integer) session.getAttribute("user_id");
        return new DataReturnMap("user", userService.getUserInfo(user_id)).getMap();
    }

    @RequestMapping("/info")
    public Map<String, Object> modifyInfo(HttpSession session, @RequestBody User user) {
        int user_id = (Integer) session.getAttribute("user_id");
        user.setId(user_id);
        userService.modifyInfo(user);
        return new OKReturnMap("信息已修改成功！").getMap();
    }

    @PostMapping("/infoIOS")
    public Map<String, Object> modifyInfoIOS(HttpSession session, @RequestParam String corporation,
                                             @RequestParam String email,
                                             @RequestParam String username,
                                             @RequestParam int industry) {
        int user_id = (Integer) session.getAttribute("user_id");
        User user = new User(username, email, corporation, industry);
        user.setId(user_id);
        userService.modifyInfo(user);
        return new OKReturnMap("信息已修改成功！").getMap();
    }

    @PostMapping("/test")
    public Map<String, Object> test(@RequestBody User user) {
        System.out.println("test");
        return new DataReturnMap("user", user).getMap();
    }

    @PostMapping("/test1")
    public Map<String, Object> test1(@RequestBody String body) {
        return new DataReturnMap("body", body).getMap();
    }
}
