package com.peaceful.cron.server.exception;

/**
 * Created by Jun on 2018/5/5.
 */
public enum CronResponseCode {

    ILLEGAL_ARGUMENT(100, "参数错误"),
    SUCCESS(200, "处理成功"),
    SYSTEM_ERROR(500, "系统错误，稍后重试"),
    CLIENT_CONNECTION_ERROR(501, "Client端无法连接，稍后重试"),

    SERVICE_NOT_EXIST(1000, "服务不存在，稍后重试");


    private int code;
    private String message;

    CronResponseCode(int code, String message) {
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
