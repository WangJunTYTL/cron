package com.peaceful.cron.client;

import com.peaceful.cron.client.core.CronJetty;

/**
 * 嵌入jetty：http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html
 *
 * Created by wangjun on 2016/11/10.
 */
public class StartCronClient {

    public static void main(String[] args) throws Exception {
        CronJetty cronClientJetty = new CronJetty();
        cronClientJetty.start();
    }

}
