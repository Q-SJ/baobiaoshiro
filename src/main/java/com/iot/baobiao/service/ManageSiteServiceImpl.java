package com.iot.baobiao.service;

import com.iot.baobiao.dao.SiteDaoInterface;
import com.iot.baobiao.dao.UserDaoInterface;
import com.iot.baobiao.dao.UserSiteDaoInterface;
import com.iot.baobiao.exception.DuplicateSiteException;
import com.iot.baobiao.jooq.tables.pojos.Site;
import com.iot.baobiao.jooq.tables.pojos.UserSite;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by ja on 2016/6/22.
 */

@Service
@Transactional
public class ManageSiteServiceImpl implements ManageSiteService {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDaoInterface userDaoInterface;

    @Autowired
    private SiteDaoInterface siteDaoInterface;

    @Autowired
    private UserSiteDaoInterface userSiteDaoInterface;

    public int addUserSite(int user_id, Map<String, String> args) {
        String domain = (String) args.get("domain");
        String url = (String) args.get("url");
        String sitename = (String) args.get("sitename");

        //先查看数据库中是否已有该网站信息，如果没有需要添加到site表里面
        Site site = siteDaoInterface.fetchOneByDomain(domain);

        int site_id;
        if (site == null) {
            //数据库中找不到这条网站，就先添加
            log.warn("数据库中没有域名为" + domain + "的网站！");
            site = new Site(null, domain, (String) args.get("url"), (String) args.get("sitename"), null);
            site_id = siteDaoInterface.insertSiteAndGetId(site);
        } else {
            site_id = site.getId();
        }

        //插入用户选择这个网站的一条记录，如果已经添加过这个网站就会出现数据完整性异常
        try {
            userSiteDaoInterface.insert(new UserSite(null, user_id, site_id, sitename, url));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateSiteException();
        }
        return site_id;
    }


    //删除自选网站
    public void deleteUserSite(int user_id, int site_id) {
        userSiteDaoInterface.deleteByUserIDAndSiteID(user_id, site_id);
    }


    public List<UserSite> queryUserSite(int user_id) {
        return userSiteDaoInterface.fetchByUserId(user_id);
    }

    //查询用户的自选网站，以列表的形式返回网站id用于在self_site表中查找招标数据
    public List<Integer> queryUserSiteID(int user_id) {
        return userSiteDaoInterface.fetchSiteIdByUserId(user_id);
    }



    //返回数据库中所有的Domain List，以便客户端进行自动补齐
    public List<String> queryDomainList() {
        return siteDaoInterface.findAllSite();
    }

    public String deleteSiteByDomain(String domain) {

        Site site = siteDaoInterface.fetchOneByDomain(domain);

        if (site == null) return "没有此网站！";
        int site_id = site.getId();

        userSiteDaoInterface.deleteBySiteID(site_id);
        siteDaoInterface.delete(site_id);
        return "删除成功！";
    }

    @Override
    public List<Site> findAll() {
        return siteDaoInterface.fetchAll();
    }

    @Override
    public Site findSiteByDomain(String domain) {
        return siteDaoInterface.fetchOneByDomain(domain);
    }

    @Override
    public List<Site> findSiteByName(String name) {
        return siteDaoInterface.fetchByName(name);
    }

}
