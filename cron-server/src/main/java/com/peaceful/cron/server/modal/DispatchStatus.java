package com.peaceful.cron.server.modal;

/**
 * Task状态机
 *
 * Created by wangjun38 on 2018/5/5.
 */
public enum DispatchStatus {
    INIT(0, "未到执行时间"),
    DISPATCH_SEND(1, "指令发送成功"),
    DISPATCH_ERROR(2, "指令发送失败"),
    JOB_SUCCESS(3, "Job执行成功"),
    JOB_EXCEPTION(4, "Job执行异常"),
    JOB_TIMEOUT(5, "Job执行超时"),;

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
