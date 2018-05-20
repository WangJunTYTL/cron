package com.peaceful.cron.client.core;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.exception.CronExceptionEnum;
import com.peaceful.cron.client.util.CronClientConfig;
import com.peaceful.cron.client.util.ExceptionUtil;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jun on 2018/5/12.
 */
public class CronJetty {

    private static boolean isStart = false;
    private static Logger logger = LoggerFactory.getLogger(CronJetty.class);

    public static synchronized void start(CronClientConfig config) {
        try {
            if (!isStart) {
                Server server = new Server(5656);

                ServletContextHandler context = new ServletContextHandler(
                        ServletContextHandler.SESSIONS);

                context.setContextPath("/cron");
                context.setResourceBase(System.getProperty("java.io.tmpdir"));
                server.setHandler(context);
                context.addServlet(CronDispatchServlet.class, "/dispatch");
                context.addServlet(CronProcessServlet.class, "/process");

                server.start();
//                server.join();
                logger.info("cron jetty start suc");
                isStart = true;
            } else {
                logger.warn("cron jetty is single instance,don't repeat create");
            }
        } catch (Exception e) {
            throw new CronException(CronExceptionEnum.CRON_CLIENT_ERROR, e);
        }
    }
}
