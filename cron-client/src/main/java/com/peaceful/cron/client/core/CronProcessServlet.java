package com.peaceful.cron.client.core;

import org.apache.http.Consts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Jun on 2018/5/12.
 */
public class CronProcessServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jobName = req.getParameter("_jobName");
        resp.setCharacterEncoding("UTF-8");
        if (jobName == null || "".equalsIgnoreCase(jobName.trim())){
            resp.getWriter().print("_jobName is empty");
            resp.getWriter().flush();
            return;
        }
        CronJob cronJob = JobProcess.get(jobName);
        if (cronJob != null) {
            resp.getWriter().print(cronJob.buildJson());
            resp.getWriter().flush();
            return;
        } else {
            resp.getWriter().print("job is empty");
            resp.getWriter().flush();
            return;
        }

    }
}
