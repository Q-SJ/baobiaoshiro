package com.iot.baobiao.dao.jooqImpl;

import com.iot.baobiao.dao.UserSiteDaoInterface;
import com.iot.baobiao.jooq.tables.daos.UserSiteDao;
import com.iot.baobiao.jooq.tables.pojos.UserSite;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.iot.baobiao.jooq.tables.UserSite.USER_SITE;

/**
 * Created by jia on 2016/7/11.
 */

@Repository
public class UserSiteDaoInterfaceImpl extends UserSiteDao implements UserSiteDaoInterface {

    @Autowired
    private DSLContext dsl;

    //由于继承了jooq自动生成的daos类，所以必须自已注入Configuration类，不然会找不到Configuration
    @Autowired
    public UserSiteDaoInterfaceImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void deleteByUserIDAndSiteID(int user_id, int site_id) {
        dsl.delete(USER_SITE)
                .where(USER_SITE.USER_ID.equal(user_id).and(USER_SITE.SITE_ID.equal(site_id)))
                .execute();
    }

    @Override
    public List<Integer> fetchSiteIdByUserId(int user_id) {
        return dsl.select(USER_SITE.SITE_ID)
                .from(USER_SITE)
                .where(USER_SITE.USER_ID.equal(user_id))
                .fetch()
                .into(Integer.class);
    }

    @Override
    public void deleteBySiteID(int site_id) {
        dsl.delete(USER_SITE)
                .where(USER_SITE.SITE_ID.equal(site_id))
                .execute();
    }

    @Override
    public void insert(UserSite userSite) {
        dsl.insertInto(USER_SITE, USER_SITE.USER_ID, USER_SITE.SITE_ID, USER_SITE.SITENAME, USER_SITE.START_URL)
                .values(userSite.getUserId(), userSite.getSiteId(), userSite.getSitename(), userSite.getStartUrl())
                .execute();
    }

    @Override
    public List<UserSite> fetchByUserId(int user_id) {
        return dsl.select()
                .from(USER_SITE)
                .where(USER_SITE.USER_ID.equal(user_id))
                .fetch()
                .into(UserSite.class);
    }
}
