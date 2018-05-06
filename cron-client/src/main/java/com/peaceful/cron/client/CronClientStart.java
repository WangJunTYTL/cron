package com.peaceful.cron.client;

import com.peaceful.cron.client.core.CronDispatchServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * 嵌入jetty：http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html
 *
 * Created by wangjun on 2016/11/10.
 */
public class CronClientStart {

    public static void main(String[] args) throws Exception {
        Server server = new Server(5656);

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);

        context.setContextPath("/cron");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        server.setHandler(context);
        // Add myself servlet  127.0.0.1:8787/dump
        context.addServlet(CronDispatchServlet.class, "/dispatch");

        // Add default servlet
//        context.addServlet(DefaultServlet.class, "/");
        server.start();
        server.join();
    }

}
