package com.peaceful.cron.client.core;

import com.peaceful.cron.client.exception.CronException;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;

import java.util.List;

/**
 * Created by Jun on 2018/5/12.
 */
public class CronJob {

    private String dispatchId;
    private String name;
    private JobStatus status;


    public void validate() {
        if (name == null || name.trim().equalsIgnoreCase("")) {
            status = JobStatus.COMMAND_ERROR;
        }
        if (dispatchId == null || dispatchId.trim().equalsIgnoreCase("")) {
            status = JobStatus.COMMAND_ERROR;
        }
        if (status == JobStatus.COMMAND_ERROR) {
            throw new CronException(status.getCode(), status.getMessage());
        }
    }

    public List<NameValuePair> buildList() {
        return Form.form()
                .add("name", name)
                .add("dispatchId", dispatchId)
                .add("code", String.valueOf(status.getCode()))
                .add("message", status.getMessage())
                .build();
    }

    @Override
    public String toString() {
        return buildJson();
    }

    public String buildJson() {
        return "{" +
                "'dispatchId:'" + dispatchId +
                ", 'name':'" + name + "'" +
                ", 'code':" + status.getCode() +
                ", 'message':'" + status.getMessage() + "'" +
                "}";
    }

    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}


