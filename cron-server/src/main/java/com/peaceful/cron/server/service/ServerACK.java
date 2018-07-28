package com.peaceful.cron.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peaceful.cron.server.dataobj.CronJobTO;
import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronDispatch;

import org.apache.http.Consts;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;
import java.util.Random;

/**
 * Created by Jun on 2018/5/6.
 */
@Service
public class ServerACK {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CronRegisterService cronServiceRegister;

    /**
     * 发送执行指令
     **/
    public String sendDispatch(CronDispatch cronDispatch) {
        logger.info("start send");
        long startTime = System.currentTimeMillis();
        try {
            String serviceName = "cron.test"; // TODO
            List<String> nodeList = cronServiceRegister.getClientList(serviceName);
            if (nodeList == null || nodeList.isEmpty()) {
                throw new CronServerException(CronResponseCode.CLIENT_CONNECTION_ERROR, "没有可用的Client");
            }
            Random rand = new Random();
            String url = nodeList.get(rand.nextInt(nodeList.size())); // 随机发配
            Request.Post("http://" + url + "/cron/dispatch")
                    .bodyForm(Form.form()
                            .add("_jobName", cronDispatch.getName())
                            .add("_dispatchId", String.valueOf(cronDispatch.getId()))
                            .build(), Consts.UTF_8)
                    .execute().returnContent();
            return url;
        } catch (ConnectException e) {
            throw new CronServerException(CronResponseCode.CLIENT_CONNECTION_ERROR, "发送指令失败");
        } catch (Exception e) {
            logger.error("发送指令失败", e);
            throw new CronServerException(CronResponseCode.SYSTEM_ERROR, "发送指令失败");
        } finally {
            logger.info("send dispatch cost:" + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

    /**
     * 发送执行指令
     **/
    public void sendDispatch(CronDispatch cronDispatch, int retryCount) {
        try {
            sendDispatch(cronDispatch);
        } catch (CronServerException e) {
            if (e.getCode() == CronResponseCode.CLIENT_CONNECTION_ERROR.getCode()) {

            }
        }
    }

    /**
     * 客户端ACK执行结果
     **/
    public CronJobTO checkDispatch(String jobName) {
        try {

            Content content = Request.Post("http://127.0.0.1:5656/cron/process")
                    .bodyForm(Form.form()
                            .add("_jobName", jobName)
                            .build())
                    .execute().returnContent();
            String process = content.asString(Consts.UTF_8);
            logger.info(process);
            if (process.startsWith("CronJob")) {
                ObjectMapper mapper = new ObjectMapper();
                CronJobTO cronJobTO = mapper.readValue(process, CronJobTO.class);
                return cronJobTO;
            } else {
                return null;
            }
        } catch (ConnectException e) {
            throw new CronServerException(CronResponseCode.CLIENT_CONNECTION_ERROR, e);
        } catch (Exception e) {
            logger.error("发送指令失败", e);
            throw new CronServerException(CronResponseCode.SYSTEM_ERROR, "发送指令失败");
        } finally {
            // ignore
        }
    }

}
