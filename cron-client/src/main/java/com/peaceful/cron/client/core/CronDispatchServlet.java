package com.peaceful.cron.client.core;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.logging.Log;
import com.peaceful.cron.client.logging.LogFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangjun on 2016/11/13.
 */
public class CronDispatchServlet extends HttpServlet {

    private Log log = LogFactory.getLog(getClass());

    static {
        CronJobRigister.add("test", new JobMock());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jobName = request.getParameter("_jobName");
        String dispatchId = request.getParameter("_dispatchId");
        DispatchDO dispatchDO = new DispatchDO();
        dispatchDO.setJobName(jobName);
        dispatchDO.setDispatchId(dispatchId);
        log.warn(System.currentTimeMillis() + ":" + jobName);

        // 这里需要尽量避免Dispatch的阻塞
        try {
            dispatchDO.validate();
            CronExecutor.commit(new IJob(dispatchDO));
            response.getWriter().print("ok");
        } catch (CronException e) {
            response.getWriter().print("fail");
        }
        response.getWriter().flush();

    }
}
