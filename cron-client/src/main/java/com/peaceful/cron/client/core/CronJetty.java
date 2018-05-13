package com.peaceful.cron.client.core;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Created by Jun on 2018/5/12.
 */
public class CronJetty {

    public void start() throws Exception {
        Server server = new Server(5656);

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);

        context.setContextPath("/cron");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        server.setHandler(context);
        context.addServlet(CronDispatchServlet.class, "/dispatch");
        context.addServlet(CronProcessServlet.class, "/process");

        server.start();
        server.join();
    }
}
