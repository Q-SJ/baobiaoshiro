package com.iot.baobiao.service;

import com.iot.baobiao.dao.SelfSiteDaoInterface;
import com.iot.baobiao.jooq.tables.pojos.SelfSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ja on 2016/6/22.
 */

@Service
public class DataService {

    @Autowired
    private SelfSiteDaoInterface selfSiteDaoInterface;

    public List<SelfSite> fetchData(List<Integer> ids,
                                    int data_id,
                                    List<String> wordList,
                                    String time,
                                    boolean own) {
        return selfSiteDaoInterface.findData(ids, data_id, wordList, time, own);
    }

    public List<SelfSite> findDataBySite(int site_id, int data_id) {
        return selfSiteDaoInterface.findDataBySite(site_id, data_id);
    }
}
