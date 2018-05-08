package com.peaceful.cron.server.modal;

/**
 * Task状态机
 *
 * Created by Juna on 2018/5/5.
 */
public enum DispatchStatus {
    INIT(0, "等待调度"),
    DISPATCH_SEND(1, "等待执行"),
    DISPATCH_ERROR(2, "执行失败"),
    JOB_SUCCESS(3, "执行成功"),
    JOB_EXCEPTION(4, "执行异常"),
    JOB_TIMEOUT(5, "执行超时"),;

    private int code;
    private String desc;

    DispatchStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static DispatchStatus getByCode(int code) {
        for (DispatchStatus status : DispatchStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
