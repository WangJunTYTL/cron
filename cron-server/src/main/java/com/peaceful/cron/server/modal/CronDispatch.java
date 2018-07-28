package com.peaceful.cron.server.modal;

import com.peaceful.cron.server.modal.enums.DispatchStatus;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by Jun on 2018/5/5.
 */
@Data
public class CronDispatch {

    private long id;
    private long serviceId;
    private String name;
    private int retryCount;
    private DispatchStatus status;
    private String ackMessage;
    private String ipPort;
    private Timestamp planDispatchTime;
    private Timestamp realDispatchTime;
    private Timestamp realExecutionTime;
    private Timestamp completeTime;

}
