package com.iot.baobiao.dao;

import com.iot.baobiao.jooq.tables.pojos.SelfSite;

import java.util.List;

/**
 * Created by jia on 2016/10/30.
 */
public interface SelfSiteDaoInterface {

    List<SelfSite> findData(List<Integer> ids, int data_id, String time, int location, String city, List<String> wordList, String fromTime, boolean own);

    List<SelfSite> findDataBySite(int site_id, int self_site_id);

    void insert(SelfSite selfSite);

    List<SelfSite> findDataAnon(int data_id, String time, int location, String city, List<String> wordList, String fromTime);
}
