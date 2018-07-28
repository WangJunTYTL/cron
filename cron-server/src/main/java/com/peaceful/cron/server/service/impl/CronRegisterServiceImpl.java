package com.peaceful.cron.server.service.impl;

import com.peaceful.cron.server.service.CronRegisterService;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jun on 2018/5/17.
 */
@Service
public class CronRegisterServiceImpl implements CronRegisterService {

    private ZkClient zkClient = new ZkClient("127.0.0.1:2181");

    @Override
    public List<String> getClientList(String serviceName) {
        return zkClient.getChildren("/cron/client/" + serviceName + "/node");
    }

    @Override
    public List<String> getServerList() {
        return null;
    }

}
