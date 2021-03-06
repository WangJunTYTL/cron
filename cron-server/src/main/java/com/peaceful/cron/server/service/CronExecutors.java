package com.peaceful.cron.server.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Jun on 2018/5/9.
 */
public class CronExecutors {

    private final static ThreadFactory threadFactoryBuilder = new ThreadFactoryBuilder()
            .setNameFormat("cron-scheduler-%d")
            .setDaemon(true)
            .build();
    public static final ScheduledExecutorService SCHEDULES = Executors.newScheduledThreadPool(8, threadFactoryBuilder);

    public static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(8,threadFactoryBuilder);

    static{

    }


}
