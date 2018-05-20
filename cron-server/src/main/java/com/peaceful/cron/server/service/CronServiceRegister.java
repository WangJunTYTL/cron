package com.peaceful.cron.server.service;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jun on 2018/5/17.
 */
@Service
public class CronServiceRegister {

    private ZkClient zkClient = new ZkClient("127.0.0.1:2181");

    public List<String> getClientList(String serviceName) {
        return zkClient.getChildren("/cron/client/" + serviceName + "/node");
    }

}
