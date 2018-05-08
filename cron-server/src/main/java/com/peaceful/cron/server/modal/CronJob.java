package com.peaceful.cron.server.modal;

import java.sql.Timestamp;

/**
 * Created by Jun on 2018/5/5.
 */
public class CronJob {

    private long id;
    private String name;
    private String cronExpression;
    private JobStatus status;
    private Timestamp nextExecutionTime;
    private Timestamp createTime;
    private Timestamp updateTime;

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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Timestamp getNextExecutionTime() {
        return nextExecutionTime;
    }

    public void setNextExecutionTime(Timestamp nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
