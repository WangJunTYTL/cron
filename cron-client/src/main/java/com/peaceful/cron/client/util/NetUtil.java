package com.peaceful.cron.client.util;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.exception.CronExceptionEnum;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Jun on 2018/5/17.
 */
public class NetUtil {

    public static String getHostAddress() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            throw new CronException(CronExceptionEnum.CRON_CLIENT_ERROR, "无法获取本地ip地址");
        }
    }
}
