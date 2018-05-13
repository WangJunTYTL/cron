package com.peaceful.cron.client.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jun on 2018/5/12.
 */
public class JobProcess {

    private static Map<String, CronJob> processList = new ConcurrentHashMap<>();

    public static void put(CronJob cronJob) {
        processList.put(cronJob.getName(), cronJob);
    }

    public static CronJob get(String jobName) {
        return processList.get(jobName);
    }
}
