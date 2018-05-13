package com.peaceful.cron.client.core;

import com.peaceful.cron.client.exception.CronException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jun on 2018/5/6.
 */
public class CronScheduler {

    private static final Map<String, Runnable> jobInstanceMap = new HashMap<>();
    private static Logger log = LoggerFactory.getLogger(CronScheduler.class);

    public static synchronized void add(String jobName, Runnable jobInstance) {
        if (jobInstanceMap.containsKey(jobName)) {
            log.warn(String.format("job:%s is exist", jobName));
        }
        if (jobInstance == null) {
            throw new CronException(JobStatus.JOB_INSTANCE_ILLEGAL.getCode(), "Job实例不可以为null");
        }

        if (jobInstance instanceof Runnable) {
            // ignore
        } else {
            throw new CronException(JobStatus.JOB_INSTANCE_ILLEGAL.getCode(), "Job对象必须继承Runnable接口");
        }

        jobInstanceMap.put(jobName, jobInstance);
    }

    public static Runnable get(String jobName) {
        return jobInstanceMap.get(jobName);
    }

}
