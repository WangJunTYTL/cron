package com.peaceful.cron.server.controller;

import com.peaceful.cron.server.service.CronDispatchService;
import com.peaceful.cron.server.dataobj.CronJobTO;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.util.JsonResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 接收Cron Client发送的ack信息
 *
 * Created by Jun on 2018/5/12.
 */
@RestController
@RequestMapping("cron")
public class CronCallbackController {

    @Autowired
    private CronDispatchService cronDispatchService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/dispatch/ack")
    public JsonResult callback(HttpServletRequest request) {
        try {
            CronJobTO cronJobTO = CronJobTO.buildCronJobTO(request);
            logger.info("ack receive:{}", cronJobTO);
            cronDispatchService.ackDispatch(cronJobTO);
            return JsonResult.SUCCESS();
        } catch (CronServerException e) {
            return JsonResult.render(e);
        }
    }
}
