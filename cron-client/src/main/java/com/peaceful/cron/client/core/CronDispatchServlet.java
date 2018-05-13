package com.peaceful.cron.client.core;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.mock.JobMock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangjun on 2016/11/13.
 */
public class CronDispatchServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(getClass());

    static {
        CronScheduler.add("test", new JobMock());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jobName = request.getParameter("_jobName");
        String dispatchId = request.getParameter("_dispatchId");
        CronJob cronJob = new CronJob();
        cronJob.setDispatchId(dispatchId);
        cronJob.setName(jobName);
        cronJob.setStatus(JobStatus.START);

        logger.info("cron dispatch servlet:{}", cronJob);
        try {
            CronExecutors.EXECUTOR.execute(new CronJobCommand(cronJob));
        } catch (CronException e) {
            logger.warn("CronException:" + e);
        } finally {
            response.getWriter().print(cronJob.buildJson());
            response.getWriter().flush();
        }
    }
}
