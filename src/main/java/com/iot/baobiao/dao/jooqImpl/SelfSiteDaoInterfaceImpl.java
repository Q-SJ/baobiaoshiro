package com.iot.baobiao.dao.jooqImpl;

import com.iot.baobiao.dao.SelfSiteDaoInterface;
import com.iot.baobiao.jooq.tables.NcArea;
import com.iot.baobiao.jooq.tables.daos.SelfSiteDao;
import com.iot.baobiao.jooq.tables.pojos.SelfSite;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.iot.baobiao.jooq.tables.NcArea.NC_AREA;
import static com.iot.baobiao.jooq.tables.SelfSite.SELF_SITE;
import static org.jooq.impl.DSL.falseCondition;
import static org.jooq.impl.DSL.trueCondition;

/**
 * Created by ja on 2016/6/22.
 */

@Repository
public class SelfSiteDaoInterfaceImpl extends SelfSiteDao implements SelfSiteDaoInterface {

    @Autowired
    private DSLContext dsl;

    //由于继承了jooq自动生成的daos类，所以必须自已注入Configuration类，不然会找不到Configuration
    @Autowired
    public SelfSiteDaoInterfaceImpl(Configuration configuration) {
        super(configuration);
    }

    public static final int NUM_PER_PAGE = 200;

    //动态生成SQL语句
    public Condition whereCondition(List<Integer> ids,
                                    int location,
                                    String city,
                                    List<String> wordList,
                                    String time,
                                    boolean own) {
        Condition condition = trueCondition();

        //是否是用户自己添加的网站
        if (ids != null) {
            if (own) condition = condition.and(SELF_SITE.URL_ID.in(ids));
            else condition = condition.and(SELF_SITE.URL_ID.notIn(ids));
        }

        //加上地点的条件
        if (location != -1) {
            condition = condition.and(SELF_SITE.POSITION.equal(location));
        } else if (city != null) {
            List<Integer> areaIDList = dsl.select(NC_AREA.NC_AREAID)
                    .from(NC_AREA)
                    .where(NC_AREA.NAME.contains(city))
                    .fetch()
                    .into(Integer.class);
            condition = condition.and(SELF_SITE.POSITION.in(areaIDList));
        }

            //加上关键字的条件
        Condition wordCondition = falseCondition();
        for (String word : wordList) {
            wordCondition = wordCondition.or(SELF_SITE.NAME.contains(word));
        }
        condition = condition.and(wordCondition);

        //加上时间的条件
        if (time != null) {
            Instant instant = Instant.ofEpochMilli(Long.parseLong(time));
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.ofHours(8));
            condition = condition.and(SELF_SITE.FETCH_TIME.greaterOrEqual(localDateTime));
        }

        return condition;
    }

    @Override
    public List<SelfSite> findData(List<Integer> ids,
                                   int data_id,
                                   String time,
                                   int location,
                                   String city,
                                   List<String> wordList,
                                   String fromTime,
                                   boolean own) {
//        return dsl.select()
//                .from(SELF_SITE)
//                .where(whereCondition(ids, wordList, time, own))
//                .orderBy(SELF_SITE.FETCH_TIME.desc(), SELF_SITE.ID.desc())
//                .limit(NUM_PER_PAGE)
//                .offset(NUM_PER_PAGE * page)
//                .fetch()
//                .into(SelfSite.class);
        SelectQuery query = dsl.selectQuery();
        query.addFrom(SELF_SITE);
        query.addConditions(whereCondition(ids, location, city, wordList, fromTime, own));
        query.addOrderBy(SELF_SITE.FETCH_TIME.desc(), SELF_SITE.ID.desc());

        if (time != null && !time.equals("")) {
            Instant instant = Instant.ofEpochMilli(Long.parseLong(time));
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.ofHours(8));
            if (data_id != -1 && localDateTime != null)
                query.addSeekAfter(DSL.val(localDateTime), DSL.val(data_id));
        }

        query.addLimit(NUM_PER_PAGE);
        return query.fetch().into(SelfSite.class);
//        if (self_site_id == -1) {
//            return dsl.select()
//                    .from(SELF_SITE)
//                    .where(whereCondition(ids, wordList, time, own))
//                    .orderBy(SELF_SITE.ID.desc())
//                    .limit(NUM_PER_PAGE)
//                    .fetch()
//                    .into(SelfSite.class);
//        } else {
//            return dsl.select()
//                    .from(SELF_SITE)
//                    .where(whereCondition(ids, wordList, time, own))
//                    .orderBy(SELF_SITE.ID.desc())
//                    .seek(self_site_id)
//                    .limit(NUM_PER_PAGE)
//                    .fetch()
//                    .into(SelfSite.class);
//        }
    }

    @Override
    public List<SelfSite> findDataBySite(int site_id, int self_site_id) {
        SelectQuery query = dsl.selectQuery();
        query.addFrom(SELF_SITE);
        query.addConditions(SELF_SITE.URL_ID.equal(site_id));
        query.addOrderBy(SELF_SITE.ID.desc());
        if (self_site_id != -1) query.addSeekAfter(DSL.val(self_site_id));
        query.addLimit(NUM_PER_PAGE);
        return query.fetch().into(SelfSite.class);
    }

    @Override
    public List<SelfSite> findDataAnon(int data_id, String time, int location, String city, List<String> wordList, String fromTime) {
        SelectQuery query = dsl.selectQuery();
        query.addFrom(SELF_SITE);
        query.addConditions(whereCondition(null, location, city, wordList, time, false));
        query.addOrderBy(SELF_SITE.FETCH_TIME.desc(), SELF_SITE.ID.desc());

        if (time != null && !time.equals("")) {
            Instant instant = Instant.ofEpochMilli(Long.parseLong(time));
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.ofHours(8));
            if (data_id != -1 && localDateTime != null)
                query.addSeekAfter(DSL.val(localDateTime), DSL.val(data_id));
        }

        query.addLimit(NUM_PER_PAGE);
        return query.fetch().into(SelfSite.class);
    }
}
