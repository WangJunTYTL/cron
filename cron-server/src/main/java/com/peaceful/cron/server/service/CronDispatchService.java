package com.peaceful.cron.server.service;

import com.peaceful.cron.server.dataobj.CronJobTO;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.CronJob;

/**
 * Created by Jun on 2018/5/12.
 */
public interface CronDispatchService {

    /**
     * 状态为INIT的Job，计划下一次执行时间，并生成对应的Dispatch指令
     **/
    void planNextDispatch(CronJob cronJob);

    /**
     * 发送执行指令
     */
    void sendDispatch(CronDispatch cronDispatch);

    /**
     * 终止执行
     **/
    void killDispatch();

    /**
     * ack Dispatch
     **/
    void ackDispatch(CronJobTO cronJobTO);

    void checkDispatch(CronJob cronJob);
}
