package com.peaceful.cron.client.core;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.exception.CronExceptionEnum;
import com.peaceful.cron.client.util.Preconditions;
import com.peaceful.cron.client.util.StringUtils;

import org.apache.http.Consts;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Jun on 2018/5/12.
 */
public class ACK {

    private static String receiveAddress = "/cron/dispatch/ack";
    private static String serverUrl = "";

    private Logger logger = LoggerFactory.getLogger(getClass());


    public ACK(String serverAddress, Integer serverPort) {
        Preconditions.checkArgument(StringUtils.isNotBlank(serverAddress), "服务端地址不可以为空");

        if (StringUtils.isNotBlank(serverAddress)) {
            serverUrl += serverAddress;
        }

        if (serverPort != null) {
            serverUrl += ":" + serverPort;
        }
        serverUrl += receiveAddress;
        logger.info("cron server address is:{}", serverAddress);
    }

    /**
     * send message to Cron Server
     **/
    public void send(CronJob job) {
        try {
            Request.Post(serverUrl)
                    .bodyForm(job.buildList(), Consts.UTF_8)
                    .execute().returnContent();
        } catch (HttpResponseException e) {
            throw new CronException(CronExceptionEnum.CRON_SERVER_ERROR, e);
        } catch (IOException e) {
            throw new CronException(CronExceptionEnum.CRON_SERVER_ERROR, e);
        }
    }


}
