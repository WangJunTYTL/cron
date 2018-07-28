package com.peaceful.cron.server.controller;

import com.google.common.collect.Maps;

import com.github.pagehelper.PageInfo;
import com.peaceful.cron.server.dataobj.CreateJobRequest;
import com.peaceful.cron.server.dataobj.CronDispatchSearch;
import com.peaceful.cron.server.dataobj.CronJobSearch;
import com.peaceful.cron.server.dataobj.ServiceRegistryRequest;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.ServiceRegistry;
import com.peaceful.cron.server.modal.enums.DispatchStatus;
import com.peaceful.cron.server.service.impl.CronManageServiceImpl;
import com.peaceful.cron.server.service.CronRegisterService;
import com.peaceful.cron.server.service.ServerACK;
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

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jun on 2018/5/5.
 */
@RestController
@RequestMapping("cron")
public class CronController {

    @Autowired
    private CronManageServiceImpl cronJobService;
    @Autowired
    private ServerACK dispatchTransaction;
    @Autowired
    private CronRegisterService serviceRegister;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping({"", "/create", "index"})
    public String index(HttpServletRequest request) {
        Map<String, Object> data = Maps.newHashMap();
        String serviceIdStr = request.getParameter("serviceId");
        ServiceRegistry service = cronJobService.findServiceById(Long.parseLong(serviceIdStr));
        data.put("service", service);
        return PageResult.render("/static/vm/cron/createJob.vm", data);
    }

    @RequestMapping("service/register")
    public Object serviceRegister(String isSubmit, HttpServletRequest request) {
        try {
            if ("true".equalsIgnoreCase(isSubmit)) {
                String name = request.getParameter("name");
                String comment = request.getParameter("comment");
                ServiceRegistryRequest serviceRegistryRequest = new ServiceRegistryRequest();
                serviceRegistryRequest.setName(name);
                serviceRegistryRequest.setComment(comment);
                cronJobService.createServiceRegister(serviceRegistryRequest);
                return JsonResult.SUCCESS();
            } else {
                return PageResult.render("/static/vm/cron/serviceRegister.vm");
            }
        } catch (CronServerException e) {
            return JsonResult.render(e);
        } catch (Exception e) {
            logger.error("注册服务报错", e);
            return JsonResult.FAIL();
        }
    }

    @RequestMapping("service/list")
    public Object serviceList() {
        return JsonResult.SUCCESS();
    }


    @RequestMapping("create/job")
    public JsonResult create(String name, String cronExpression, HttpServletRequest request) {
        try {
            CreateJobRequest newJobDO = new CreateJobRequest();
            newJobDO.setName(name);
            newJobDO.setCronExpression(cronExpression);
            String serviceIdStr = request.getParameter("serviceId");
            newJobDO.setServiceId(Long.parseLong(serviceIdStr));
            cronJobService.createCronJob(newJobDO);
            return JsonResult.SUCCESS();
        } catch (CronServerException e) {
            return JsonResult.render(e);
        } catch (Exception e) {
            logger.error("创建任务出现错误", e);
            return JsonResult.FAIL();
        }
    }

    @RequestMapping("service/node")
    public String viewServiceNode() {
        Map<String, Object> data = Maps.newHashMap();
        List<String> nodeList = serviceRegister.getClientList("cron.test"); // TODO
        data.put("nodeList", nodeList);
        return PageResult.render("/static/vm/cron/serviceNodeListAjax.vm", data);
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
            String serviceIdStr = request.getParameter("serviceId");
            if (StringUtils.isNotBlank(serviceIdStr)) {
                cronJobSearch.setServiceId(Long.parseLong(serviceIdStr));
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
            search.setPageSize(10);
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
            List<String> nodeList = serviceRegister.getClientList("cron.test"); // TODO
            data.put("nodeList", nodeList);
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
