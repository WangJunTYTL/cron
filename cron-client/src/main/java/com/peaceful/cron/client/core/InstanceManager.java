package com.peaceful.cron.client.core;

/**
 * Created by wangjun38 on 2018/5/12.
 */
public class InstanceManager {


    private static InstanceManager instanceManager = null;
    private static boolean isInstanced = false;
    private ACK ack;

    public static synchronized InstanceManager getSingleInstance() {
        if (!isInstanced) {
            instanceManager = new InstanceManager();
            instanceManager.ack = new ACK("http://127.0.0.1", 8787);
            isInstanced = true;
        }
        return instanceManager;

    }


    public ACK getAck() {
        return ack;
    }
}
