package com.peaceful.cron.server.controller;

import com.peaceful.cron.server.util.PageResult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangjun38 on 2018/5/6.
 */
@RestController
public class WelcomeController {

    @RequestMapping({"", "/", "index"})
    public String index() {
        return PageResult.render("/static/index.vm");
    }
}
