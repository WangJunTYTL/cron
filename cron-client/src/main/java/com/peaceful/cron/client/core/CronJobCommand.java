package com.peaceful.cron.client.core;

import com.peaceful.cron.client.exception.CronException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jun on 2018/5/6.
 */
public class CronJobCommand implements Runnable {

    private Logger log = LoggerFactory.getLogger(getClass());
    private CronJob cronJob;
    private Runnable delegation;
    private ACK ack;

    public CronJobCommand(CronJob cronJob) {
        this.cronJob = cronJob;
        JobProcess.put(cronJob);
        ack = InstanceManager.getSingleInstance().getAck();
    }

    @Override
    public void run() {
        try {
            // 校验指令
            try {
                cronJob.validate();
            } catch (CronException e) {
                cronJob.setStatus(JobStatus.COMMAND_ERROR);
                ack.send(cronJob);
                return;
            }

            delegation = CronScheduler.get(cronJob.getName());
            if (delegation == null) {
                cronJob.setStatus(JobStatus.NOT_EXIST);
                ack.send(cronJob);
                return;
            }

            delegation.run();

            cronJob.setStatus(JobStatus.END);
            // TODO Job如何终止
        } catch (Exception e) {
            log.error("Error Job:" + cronJob.getName(), e);
            cronJob.setStatus(JobStatus.ERROR);
        } finally {
            ack.send(cronJob);
        }
    }
}
