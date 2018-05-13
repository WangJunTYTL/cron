package com.peaceful.cron.server.dataobj;

import com.google.common.base.Preconditions;

import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;
import com.peaceful.cron.server.modal.CronDispatch;
import com.peaceful.cron.server.modal.DispatchStatus;

import java.sql.Timestamp;

/**
 * Created by Jun on 2018/5/5.
 */
public class CronUpdateDispatchDO {

    private long id;
    private DispatchStatus status;
    private int ackCode;
    private Timestamp completeTime;


    public void validate() {
        try {
            Preconditions.checkArgument(id > 0, "参数无效");
        } catch (IllegalArgumentException e) {
            throw new CronServerException(CronResponseCode.ILLEGAL_ARGUMENT, e.getMessage());
        }
    }

    public CronDispatch buildUpdateRequest() {
        validate();

        CronDispatch cronDispatch = new CronDispatch();
        cronDispatch.setId(id);
        cronDispatch.setStatus(status);
        cronDispatch.setAckCode(ackCode);
        cronDispatch.setCompleteTime(completeTime);
        return cronDispatch;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DispatchStatus getStatus() {
        return status;
    }

    public void setStatus(DispatchStatus status) {
        this.status = status;
    }

    public int getAckCode() {
        return ackCode;
    }

    public void setAckCode(int ackCode) {
        this.ackCode = ackCode;
    }

    public Timestamp getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Timestamp completeTime) {
        this.completeTime = completeTime;
    }
}
