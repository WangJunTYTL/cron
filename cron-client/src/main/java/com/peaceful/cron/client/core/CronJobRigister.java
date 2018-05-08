package com.peaceful.cron.client.core;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.exception.CronStatusCode;
import com.peaceful.cron.client.logging.Log;
import com.peaceful.cron.client.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juna on 2018/5/6.
 */
public class CronJobRigister {

    private static final Map<String, Object> jobInstanceMap = new HashMap<>();
    private static Log log = LogFactory.getLog(CronJobRigister.class);

    public static synchronized void add(String jobName, Object jobInstance) {
        if (jobInstanceMap.containsKey(jobName)) {
            log.warn(String.format("job:%s is exist", jobName));
        }
        if (jobInstance == null) {
            throw new CronException(CronStatusCode.ILLEGAL_INSTANCE, "Job实例不可以为null");
        }

        if (jobInstance instanceof Runnable) {
            // ignore
        } else {
            throw new CronException(CronStatusCode.ILLEGAL_INSTANCE, "Job对象必须继承Runnable接口");
        }

        jobInstanceMap.put(jobName, jobInstance);
    }

    public static Runnable get(String jobName) {
        return (Runnable) jobInstanceMap.get(jobName);
    }

}
