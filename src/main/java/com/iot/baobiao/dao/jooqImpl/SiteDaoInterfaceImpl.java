package com.iot.baobiao.dao.jooqImpl;

import com.iot.baobiao.dao.SiteDaoInterface;
import com.iot.baobiao.jooq.tables.daos.SiteDao;
import com.iot.baobiao.jooq.tables.pojos.Site;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.iot.baobiao.jooq.tables.Site.SITE;


/**
 * Created by ja on 2016/6/28.
 */

@Repository
public class SiteDaoInterfaceImpl extends SiteDao implements SiteDaoInterface {

    @Autowired
    private DSLContext dsl;

    //由于继承了jooq自动生成的daos类，所以必须自已注入Configuration类，不然会找不到Configuration
    @Autowired
    public SiteDaoInterfaceImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public int insertSiteAndGetId(com.iot.baobiao.jooq.tables.pojos.Site site) {
        return dsl.insertInto(SITE, SITE.DOMAIN, SITE.START_URL, SITE.SITENAME)
                .values(site.getDomain(), site.getStartUrl(), site.getSitename())
                .returning(SITE.ID)
                .fetchOne()
                .getValue(SITE.ID);
    }

    @Override
    public List<String> findAllSite() {
        return dsl.select(SITE.DOMAIN)
                .from(SITE)
                .fetch()
                .into(String.class);
    }

    @Override
    public Site fetchOneByDomain(String domain) {
        Record record = dsl.select()
                .from(SITE)
                .where(SITE.DOMAIN.equal(domain))
                .fetchOne();
        return record == null ? null : record.into(Site.class);
    }

    @Override
    public void delete(int site_id) {
        dsl.delete(SITE)
                .where(SITE.ID.equal(site_id))
                .execute();
    }

    @Override
    public List<Site> fetchAll() {
        return dsl.select()
                .from(SITE)
                .fetch()
                .into(Site.class);
    }

    @Override
    public List<Site> fetchByName(String name) {
        return dsl.select()
                .from(SITE)
                .where(SITE.SITENAME.contains(name))
                .fetch()
                .into(Site.class);
    }
}
