package com.peaceful.cron.server.service;

import com.github.pagehelper.PageInfo;
import com.peaceful.cron.server.dataobj.CreateJobRequest;
import com.peaceful.cron.server.dataobj.CronDispatchSearch;
import com.peaceful.cron.server.dataobj.CronJobSearch;
import com.peaceful.cron.server.dataobj.ServiceRegistryRequest;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.ServiceRegistry;

import java.util.List;

/**
 * Created by Jun on 2018/5/12.
 */
public interface CronManageService {

    /**
     * 创建Job
     **/
    void createCronJob(CreateJobRequest newCronJobDO);

    /**
     * Job检索
     **/
    public PageInfo<CronJob> searchCronJob(CronJobSearch search);

    /**
     * 调度记录检索
     **/
    public PageInfo<CronDispatch> searchCronDispatchList(CronDispatchSearch search);


    void createServiceRegister(ServiceRegistryRequest request);

    List<ServiceRegistry> findAllService();

    ServiceRegistry findServiceById(long id);
}
