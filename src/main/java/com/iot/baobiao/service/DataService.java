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
                                    String time,
                                    int location,
                                    String city,
                                    List<String> wordList,
                                    String fromTime,
                                    boolean own) {
        return selfSiteDaoInterface.findData(ids, data_id, time, location, city, wordList, fromTime, own);
    }

    public List<SelfSite> findDataBySite(int site_id, int data_id) {
        return selfSiteDaoInterface.findDataBySite(site_id, data_id);
    }

    public List<SelfSite> fetchDataAnon(int data_id,
                                        String time,
                                        int location,
                                        String city,
                                        List<String> wordList,
                                        String fromTime) {
        return selfSiteDaoInterface.findDataAnon(data_id, time, location, city, wordList, fromTime);
    }
}
