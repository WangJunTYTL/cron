package com.peaceful.cron.server.modal;

import java.sql.Timestamp;

/**
 * Created by wangjun38 on 2018/5/5.
 */
public class CronDispatch {

    private long id;
    private String name;
    private int retryCount;
    private DispatchStatus status;
    private Timestamp dispatchTime;
    private Timestamp completeTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public DispatchStatus getStatus() {
        return status;
    }

    public void setStatus(DispatchStatus status) {
        this.status = status;
    }

    public Timestamp getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Timestamp dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public Timestamp getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Timestamp completeTime) {
        this.completeTime = completeTime;
    }
}
