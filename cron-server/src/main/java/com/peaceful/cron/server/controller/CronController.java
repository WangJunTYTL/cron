package com.peaceful.cron.server.controller;

import com.google.common.collect.Maps;

import com.peaceful.cron.server.dataobj.CronCreateJobDO;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.service.CronEngine;
import com.peaceful.cron.server.service.DispatchTransaction;
import com.peaceful.cron.server.util.JsonResult;
import com.peaceful.cron.server.util.PageResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by wangjun38 on 2018/5/5.
 */
@RestController
@RequestMapping("cron")
public class CronController {

    @Autowired
    private CronEngine cronJobService;
    @Autowired
    private DispatchTransaction dispatchTransaction;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping({"", "/", "index"})
    public String index() {
        return PageResult.render("/static/vm/cron/create.vm");
    }

    @RequestMapping("join")
    public String joinService() {
        return PageResult.render("/static/vm/cron/join.vm");
    }

    @RequestMapping("create")
    public JsonResult create(String name, String cronExpression) {
        try {
            CronCreateJobDO newJobDO = new CronCreateJobDO();
            newJobDO.setName(name);
            newJobDO.setCronExpression(cronExpression);
            cronJobService.createCronJob(newJobDO);
            return JsonResult.SUCCESS();
        } catch (CronServerException e) {
            return JsonResult.render(e);
        } catch (Exception e) {
            return JsonResult.FAIL();
        }
    }

    @RequestMapping("job/list")
    public String findCronJobList(String isAjax) {
        try {
            List<CronJob> cronJobList = cronJobService.findCronJobList();
            Map<String, Object> data = Maps.newHashMap();
            data.put("cronJobList", cronJobList);
            if ("true".equalsIgnoreCase(isAjax)) {
                return PageResult.render("/static/vm/cron/cronJobListAjax.vm", data);
            } else {
                return PageResult.render("/static/vm/cron/cronJobList.vm", data);
            }
        } catch (Exception e) {
            return PageResult.FAIL(e);
        }
    }

    @RequestMapping("dispatch/list")
    public String findCronDispatchList(String isAjax, String name) {
        try {
            List<CronDispatch> cronJobList = cronJobService.findCronDispatchList(name);
            Map<String, Object> data = Maps.newHashMap();
            data.put("cronDispatchList", cronJobList);
            if ("true".equalsIgnoreCase(isAjax)) {
                return PageResult.render("/static/vm/cron/cronDispatchListAjax.vm", data);
            } else {
                return PageResult.render("/static/vm/cron/cronDispatchList.vm", data);
            }
        } catch (Exception e) {
            return PageResult.FAIL(e);
        }
    }

    @RequestMapping("/dispatch/ack")
    public JsonResult callback(String _dispatchId) {
        try {
            if (StringUtils.isBlank(_dispatchId)) {
                logger.error("指令不合法");
            }
            dispatchTransaction.ack(Long.valueOf(_dispatchId));
            return JsonResult.SUCCESS();
        } catch (CronServerException e) {
            return JsonResult.render(e);
        }
    }
}
