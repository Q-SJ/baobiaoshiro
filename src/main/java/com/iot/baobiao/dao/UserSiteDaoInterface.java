package com.iot.baobiao.dao;

import com.iot.baobiao.jooq.tables.pojos.UserSite;

import java.util.List;

/**
 * Created by jia on 2016/10/30.
 */
public interface UserSiteDaoInterface {

    void deleteByUserIDAndSiteID(int user_id, int site_id);

    List<Integer> fetchSiteIdByUserId(int user_id);

    void deleteBySiteID(int site_id);

    void insert(UserSite userSite);

    List<UserSite> fetchByUserId(int user_id);
}
