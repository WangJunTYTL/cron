package com.peaceful.cron.client.core;

import com.peaceful.cron.client.logging.Log;
import com.peaceful.cron.client.logging.LogFactory;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 * Created by Jun on 2018/5/6.
 */
public class IJob implements Runnable {

    private Log log = LogFactory.getLog(getClass());
    private DispatchDO dispatchDO;

    public IJob(DispatchDO dispatchDO) {
        this.dispatchDO = dispatchDO;
    }

    @Override
    public void run() {
        String jobName = dispatchDO.getJobName();
        Runnable runnable = CronJobRigister.get(jobName);
        if (runnable == null) {
            CronJobRigister.add(jobName, new JobMock());
            runnable = CronJobRigister.get(jobName);
        }

        long startTime = System.currentTimeMillis();
        try {
            runnable.run();
        } catch (Exception e) {
            log.error("Job执行抛出异常", e);
        } finally {
            try {
                Request.Post("http://127.0.0.1:8787/cron/dispatch/ack")
                        .bodyForm(Form.form()
                                .add("_jobName", jobName)
                                .add("_dispatchId", dispatchDO.getDispatchId())
                                .build())
                        .execute().returnContent();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("cron dispatch ack error", e);
            }

            log.warn(jobName + " cost:" + (System.currentTimeMillis() - startTime) + "ms");
        }
    }
}
