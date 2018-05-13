package com.peaceful.cron.client.util;

/**
 * Created by wangjun38 on 2018/5/12.
 */
public class StringUtils {

    public static boolean isNotBlank(String str){
        if (str == null || "".equalsIgnoreCase(str.trim())){
            return false;
        }
        return true;
    }
}
