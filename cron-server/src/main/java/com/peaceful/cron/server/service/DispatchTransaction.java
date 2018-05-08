package com.peaceful.cron.server.service;

import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronDispatch;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Juna on 2018/5/6.
 */
@Service
public class DispatchTransaction {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CronService cronEngine;

    /**
     * 发送执行指令
     **/
    public void send(CronDispatch cronDispatch) {
        try {
            Request.Post("http://127.0.0.1:5656/cron/dispatch")
                    .bodyForm(Form.form()
                            .add("_jobName", cronDispatch.getName())
                            .add("_dispatchId", String.valueOf(cronDispatch.getId()))
                            .build())
                    .execute().returnContent();
        } catch (Exception e) {
            logger.error("发送指令失败", e);
            throw new CronServerException(CronResponseCode.SYSTEM_ERROR, "发送指令失败");
        }
    }

    /**
     * 客户端ACK执行结果
     **/
    public void ack(long dispatchId) {
        cronEngine.updateJobStatus2INIT(dispatchId);
    }

}
