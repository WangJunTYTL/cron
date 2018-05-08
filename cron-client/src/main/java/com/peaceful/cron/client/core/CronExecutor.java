package com.peaceful.cron.client.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Juna on 2018/5/6.
 */
public class CronExecutor {

    private static Executor executor = Executors.newCachedThreadPool();

    public static void commit(IJob job){
        executor.execute(job);
    }
}
