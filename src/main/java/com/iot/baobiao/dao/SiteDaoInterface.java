package com.iot.baobiao.dao;

import com.iot.baobiao.jooq.tables.pojos.Site;

import java.util.List;

/**
 * Created by jia on 2016/10/30.
 */
public interface SiteDaoInterface {

    int insertSiteAndGetId(Site site);

    List<String> findAllSite();

    Site fetchOneByDomain(String domain);

    void delete(int site_id);

    List<Site> fetchAll();

    List<Site> fetchByName(String name);

    String fetchUrlByID(int site_id);

    void update(Site site);
}
