package com.peaceful.cron.server.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jun on 2018/5/17.
 */
@Service
public interface CronRegisterService {

    /**
     * 获取客户端机器列表
     **/
    List<String> getClientList(String serviceName);

    /**
     * 获取服务端机器列表
     **/
    List<String> getServerList();

}
