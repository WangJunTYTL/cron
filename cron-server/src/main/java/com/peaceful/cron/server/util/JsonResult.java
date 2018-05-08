package com.peaceful.cron.server.util;

import com.peaceful.cron.server.exception.CronResponseCode;
import com.peaceful.cron.server.exception.CronServerException;

/**
 * Created by Juna on 2018/4/19.
 */
public class JsonResult {

    private int code;
    private String message;

    public static JsonResult render(CronResponseCode e) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(e.getCode());
        jsonResult.setMessage(e.getMessage());
        return jsonResult;
    }

    public static JsonResult render(CronServerException e) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(e.getCode());
        jsonResult.setMessage(e.getMessage());
        return jsonResult;
    }

    public static JsonResult render(CronResponseCode responseCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(responseCode.getCode());
        jsonResult.setMessage(message);
        return jsonResult;
    }



    public static JsonResult SUCCESS() {
        return render(CronResponseCode.SUCCESS);
    }

    public static JsonResult SUCCESS(String message) {
        return render(CronResponseCode.SUCCESS,message);
    }

    public static JsonResult FAIL() {
        return render(CronResponseCode.SYSTEM_ERROR);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
