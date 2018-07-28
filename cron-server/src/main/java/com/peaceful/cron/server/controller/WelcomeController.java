package com.peaceful.cron.server.controller;

import com.google.common.collect.Maps;

import com.peaceful.cron.server.modal.ServiceRegistry;
import com.peaceful.cron.server.service.CronManageService;
import com.peaceful.cron.server.util.PageResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by Jun on 2018/5/6.
 */
@RestController
public class WelcomeController {

    @Autowired
    private CronManageService cronManageService;

    @RequestMapping({"", "/", "index"})
    public String index() {
        Map<String, Object> data = Maps.newHashMap();
        List<ServiceRegistry> serviceRegistryList = cronManageService.findAllService();
        data.put("serviceList", serviceRegistryList);
        return PageResult.render("/static/index.vm",data);
    }
}
