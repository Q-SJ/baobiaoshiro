package com.iot.baobiao.controller;

import com.iot.baobiao.pojo.Discount;
import com.iot.baobiao.service.PropertiesService;
import com.iot.baobiao.util.DataReturnMap;
import com.iot.baobiao.util.OKReturnMap;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by jia on 17-1-11.
 */
@RestController
@RequestMapping("/discount")
public class DiscountController {

    private final Logger logger = LoggerFactory.getLogger(DiscountController.class);

    @Autowired
    PropertiesService propertiesService;

    @RequestMapping("/recent")
    public Map<String, Object> recentDiscount() {
        return new DataReturnMap("discount", propertiesService.getDiscount()).getMap();
    }

    @RequestMapping("/update")
    @RequiresRoles("admin")
    public Map<String, Object> updateDiscount(@RequestBody Discount discount, HttpSession session) {
        propertiesService.updateDiscount(discount);
        logger.info("管理员" + session.getAttribute("phonenum") + "修改了活动信息：" + discount);
        return new OKReturnMap("活动信息已更新成功！").getMap();
    }
}
