package com.peaceful.cron.client.core;

/**
 * Created by Jun on 2018/5/12.
 */
public enum JobStatus {

    COMMAND_ERROR(101, "Job指令有误"),
    START(200, "开始执行"),
    END(201, "执行完毕"),
    ERROR(500, "Job执行抛出异常"),
    NOT_EXIST(501, "本地未发现该Job"),
    INTERRUPTED(502, "任务被打断"),
    JOB_INSTANCE_ILLEGAL(504, "Job必须继承Runnable接口"),;

    private int code;
    private String message;

    JobStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
