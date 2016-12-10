package com.iot.baobiao.service;

import com.iot.baobiao.jooq.tables.pojos.Site;
import com.iot.baobiao.jooq.tables.pojos.UserSite;

import java.util.List;
import java.util.Map;

/**
 * Created by jia on 2016/11/3.
 */
public interface ManageSiteService {

    int addUserSite(int user_id, Map<String, String> args);

    void deleteUserSite(int user_id, int site_id);

    List<UserSite> queryUserSite(int user_id);

    List<Integer> queryUserSiteID(int user_id);

    List<String> queryDomainList();

    String deleteSiteByDomain(String domain);

    List<Site> findAll();

    Site findSiteByDomain(String url);

    List<Site> findSiteByName(String name);
}
