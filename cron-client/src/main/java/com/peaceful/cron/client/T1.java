package com.peaceful.cron.client;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Jun on 2018/5/11.
 */
public class T1 {

    public static void main(String[] args) throws IOException, InterruptedException {
        long startTiem = System.currentTimeMillis();
        Process process =  Runtime.getRuntime().exec("sh /Users/wang/test02.sh");
        process.waitFor(); // 等待end
        System.out.println(process.exitValue());
        System.out.println("cost:" + (System.currentTimeMillis() - startTiem) + "ms");
    }
}
