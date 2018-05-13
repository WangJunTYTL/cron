package com.peaceful.cron.server.controller;

import com.google.common.collect.Maps;

import com.github.pagehelper.PageInfo;
import com.peaceful.cron.server.dataobj.CronCreateJobDO;
import com.peaceful.cron.server.dataobj.CronDispatchSearch;
import com.peaceful.cron.server.dataobj.CronJobSearch;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.DispatchStatus;
import com.peaceful.cron.server.service.CronServiceImpl;
import com.peaceful.cron.server.service.ServerACK;
import com.peaceful.cron.server.util.JsonResult;
import com.peaceful.cron.server.util.PageResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jun on 2018/5/5.
 */
@RestController
@RequestMapping("cron")
public class CronController {

    @Autowired
    private CronServiceImpl cronJobService;
    @Autowired
    private ServerACK dispatchTransaction;

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

    @RequestMapping("job/search")
    public String searchCronJobList(String isAjax, HttpServletRequest request) {
        try {
            CronJobSearch cronJobSearch = new CronJobSearch();
            String pageNumStr = request.getParameter("pageNum");
            if (StringUtils.isNotBlank(pageNumStr)) {
                cronJobSearch.setPageNo(Integer.parseInt(pageNumStr));
            }
            String jobName = request.getParameter("jobName");
            if (StringUtils.isNotBlank(jobName)) {
                cronJobSearch.setName(jobName);
            }

            PageInfo<CronJob> cronJobList = cronJobService.searchCronJob(cronJobSearch);
            Map<String, Object> data = Maps.newHashMap();
            data.put("pageInfo", cronJobList);
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
    public String findCronDispatchList(String isAjax, HttpServletRequest request) {
        try {
            CronDispatchSearch search = new CronDispatchSearch();
            search.setPageSize(5);
            String pageNumStr = request.getParameter("pageNum");
            if (StringUtils.isNotBlank(pageNumStr)) {
                search.setPageNum(Integer.parseInt(pageNumStr));
            }
            String name = request.getParameter("name");
            search.setName(name);
            Map<String, Object> data = Maps.newHashMap();
            data.put("jobName", name);
            PageInfo<CronDispatch> pageInfo = cronJobService.searchCronDispatchList(search);


            if (pageInfo.getTotal() != 0) {
                CronDispatchSearch countSearch = new CronDispatchSearch();
                countSearch.setName(name);
                countSearch.setPageSize(1);
                countSearch.setStatus(DispatchStatus.JOB_SUCCESS);
                long sucCount = cronJobService.searchCronDispatchList(countSearch).getTotal();
                countSearch.setStatus(DispatchStatus.INIT);
                long initCount = cronJobService.searchCronDispatchList(countSearch).getTotal();
                countSearch.setStatus(null);
                long total = cronJobService.searchCronDispatchList(countSearch).getTotal();
                long failCount = total - initCount - sucCount;
                data.put("sucCount", sucCount);
                data.put("failCount", failCount);
            }
            data.put("pageInfo", pageInfo);
            if ("true".equalsIgnoreCase(isAjax)) {
                return PageResult.render("/static/vm/cron/cronDispatchListAjax.vm", data);
            } else {
                return PageResult.render("/static/vm/cron/cronDispatchList.vm", data);
            }
        } catch (Exception e) {
            return PageResult.FAIL(e);
        }
    }


}
