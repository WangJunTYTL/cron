package com.peaceful.cron.client.mock;

import com.peaceful.cron.client.core.CronJetty;
import com.peaceful.cron.client.core.CronScheduler;

/**
 * Created by Jun on 2018/5/12.
 */
public class MockMain {

    public static void main(String[] args) throws Exception {
        CronScheduler.add("myJob", new JobMock());
        CronScheduler.add("a.b.c", new JobMock());

        CronJetty cronClientJetty = new CronJetty();
        cronClientJetty.start();
    }
}
