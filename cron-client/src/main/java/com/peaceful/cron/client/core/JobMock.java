package com.peaceful.cron.client.core;

/**
 * Created by Juna on 2018/5/6.
 */
public class JobMock implements Runnable {

    @Override
    public void run() {
        System.err.println("hello world!");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
