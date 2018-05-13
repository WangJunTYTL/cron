package com.peaceful.cron.server.service;

import com.github.pagehelper.PageInfo;
import com.peaceful.cron.server.dataobj.CronDispatchSearch;
import com.peaceful.cron.server.dataobj.CronJobSearch;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.CronJob;
import com.peaceful.cron.server.modal.DispatchStatus;
import com.peaceful.cron.server.modal.JobStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jun on 2018/5/5.
 */
@Service
public class CronEngineStart {


    @Autowired
    private CronServiceImpl cronService;
    @Autowired
    private CronDispatchServiceImpl cronDispatchService;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public CronEngineStart() {
        CronExecutors.SCHEDULES.scheduleAtFixedRate(new PlanNextDispatch(), 1000, 500, TimeUnit.MILLISECONDS);
        CronExecutors.SCHEDULES.scheduleAtFixedRate(new SendDispatch(), 1000, 500, TimeUnit.MILLISECONDS);
        CronExecutors.SCHEDULES.scheduleAtFixedRate(new CheckDispatchStatus(), 1, 5, TimeUnit.SECONDS);
    }


    class PlanNextDispatch implements Runnable {

        @Override
        public void run() {
            try {
                CronJobSearch cronJobSearch = new CronJobSearch();
                cronJobSearch.setStatus(JobStatus.INIT);
                cronJobSearch.setPageSize(1000);
                PageInfo<CronJob> cronJobPageInfo = cronService.searchCronJob(cronJobSearch);
                if (cronJobPageInfo.getTotal() > 0) {
                    cronJobPageInfo.getList().forEach(cronJob -> cronDispatchService.planNextDispatch(cronJob));
                }
            } catch (Exception e) {
                logger.error("Cron PlanNextDispatch Error", e);
            }
        }
    }

    class SendDispatch implements Runnable {
        @Override
        public void run() {
            try {
                CronDispatchSearch cronDispatch = new CronDispatchSearch();
                cronDispatch.setStatus(DispatchStatus.INIT);
                cronDispatch.setPageSize(1000);
                cronDispatch.setDispatchTimeEnd(new Timestamp(System.currentTimeMillis()));
                PageInfo<CronDispatch> cronDispatchPageInfo = cronService.searchCronDispatchList(cronDispatch);
                if (cronDispatchPageInfo.getTotal() > 0) {
                    cronDispatchPageInfo.getList().forEach(dispatch -> cronDispatchService.sendDispatch(dispatch));
                }
            } catch (Exception e) {
                logger.error("Cron SendDispatch Error", e);
            }
        }
    }

    class CheckDispatchStatus implements Runnable {

        @Override
        public void run() {
            try {
                CronJobSearch search = new CronJobSearch();
                // 超时1分钟的任务，应该判断是否需要重新执行
                search.setNextExecutionTimeEnd(new Timestamp(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(2)));
                search.setPageSize(1000);
                PageInfo<CronJob> cronJobPageInfo = cronService.searchCronJob(search);
                if (cronJobPageInfo.getTotal() > 0) {
                    cronJobPageInfo.getList().forEach(cronJob -> cronDispatchService.checkDispatch(cronJob));
                }
            } catch (Exception e) {
                logger.error("Cron CheckDispatchStatus Error", e);
            }
        }
    }


}
