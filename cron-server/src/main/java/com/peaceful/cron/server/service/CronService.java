package com.peaceful.cron.server.service;

import com.github.pagehelper.PageInfo;
import com.peaceful.cron.server.dataobj.CronCreateJobDO;
import com.peaceful.cron.server.dataobj.CronDispatchSearch;
import com.peaceful.cron.server.dataobj.CronJobSearch;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.CronJob;

/**
 * Created by Jun on 2018/5/12.
 */
public interface CronService {

    /**
     * 创建Job
     **/
    void createCronJob(CronCreateJobDO newCronJobDO);

    /**
     * Job检索
     **/
    public PageInfo<CronJob> searchCronJob(CronJobSearch search);

    /**
     * 调度记录检索
     **/
    public PageInfo<CronDispatch> searchCronDispatchList(CronDispatchSearch search);
}
