package com.peaceful.cron.server.modal.enums;

/**
 * Task状态机
 *
 * Created by Jun on 2018/5/5.
 */
public enum JobStatus {
    INIT(0, "初始化"),
    WAIT(1, "挂起"),
    START(2, "运行中"),

    SUSPEND(100, "暂停"),;

    private int code;
    private String desc;

    JobStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static JobStatus getByCode(int code) {
        for (JobStatus jobStatus : JobStatus.values()) {
            if (jobStatus.code == code) {
                return jobStatus;
            }
        }
        return null;
    }
}
