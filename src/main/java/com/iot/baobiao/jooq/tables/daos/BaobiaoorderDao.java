/**
 * This class is generated by jOOQ
 */
package com.iot.baobiao.jooq.tables.daos;


import com.iot.baobiao.jooq.tables.Baobiaoorder;
import com.iot.baobiao.jooq.tables.records.BaobiaoorderRecord;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class BaobiaoorderDao extends DAOImpl<BaobiaoorderRecord, com.iot.baobiao.jooq.tables.pojos.Baobiaoorder, Integer> {

    /**
     * Create a new BaobiaoorderDao without any configuration
     */
    public BaobiaoorderDao() {
        super(Baobiaoorder.BAOBIAOORDER, com.iot.baobiao.jooq.tables.pojos.Baobiaoorder.class);
    }

    /**
     * Create a new BaobiaoorderDao with an attached configuration
     */
    @Autowired
    public BaobiaoorderDao(Configuration configuration) {
        super(Baobiaoorder.BAOBIAOORDER, com.iot.baobiao.jooq.tables.pojos.Baobiaoorder.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.iot.baobiao.jooq.tables.pojos.Baobiaoorder object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.Baobiaoorder> fetchById(Integer... values) {
        return fetch(Baobiaoorder.BAOBIAOORDER.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.iot.baobiao.jooq.tables.pojos.Baobiaoorder fetchOneById(Integer value) {
        return fetchOne(Baobiaoorder.BAOBIAOORDER.ID, value);
    }

    /**
     * Fetch records that have <code>userid IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.Baobiaoorder> fetchByUserid(Integer... values) {
        return fetch(Baobiaoorder.BAOBIAOORDER.USERID, values);
    }

    /**
     * Fetch records that have <code>outtradeno IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.Baobiaoorder> fetchByOuttradeno(String... values) {
        return fetch(Baobiaoorder.BAOBIAOORDER.OUTTRADENO, values);
    }

    /**
     * Fetch records that have <code>tradeno IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.Baobiaoorder> fetchByTradeno(String... values) {
        return fetch(Baobiaoorder.BAOBIAOORDER.TRADENO, values);
    }

    /**
     * Fetch records that have <code>amount IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.Baobiaoorder> fetchByAmount(Double... values) {
        return fetch(Baobiaoorder.BAOBIAOORDER.AMOUNT, values);
    }

    /**
     * Fetch records that have <code>time IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.Baobiaoorder> fetchByTime(LocalDateTime... values) {
        return fetch(Baobiaoorder.BAOBIAOORDER.TIME, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.Baobiaoorder> fetchByStatus(Integer... values) {
        return fetch(Baobiaoorder.BAOBIAOORDER.STATUS, values);
    }

    /**
     * Fetch records that have <code>phonenum IN (values)</code>
     */
    public List<com.iot.baobiao.jooq.tables.pojos.Baobiaoorder> fetchByPhonenum(String... values) {
        return fetch(Baobiaoorder.BAOBIAOORDER.PHONENUM, values);
    }
}
