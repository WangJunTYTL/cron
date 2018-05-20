package com.peaceful.cron.client.util;

/**
 * Created by Jun on 2018/5/12.
 */
public class StringUtils {

    public static boolean isNotBlank(String str) {
        if (str == null || "".equalsIgnoreCase(str.trim())) {
            return false;
        }
        return true;
    }

    public static boolean isBlank(String str) {
        if (isNotBlank(str)) {
            return false;
        }
        return true;
    }
}
