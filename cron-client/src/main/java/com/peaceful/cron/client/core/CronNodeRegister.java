package com.peaceful.cron.client.core;

import com.peaceful.cron.client.util.CronClientConfig;
import com.peaceful.cron.client.util.NetUtil;

import org.I0Itec.zkclient.ZkClient;

/**
 * Created by Jun on 2018/5/17.
 */
public class CronNodeRegister {


    private static final ZkClient zkClient = new ZkClient("127.0.0.1:2181", 30000);

    public static void publish(CronClientConfig config) {
        String path = "/cron/client/" + config.getClientId() + "/node";
        zkClient.createPersistent(path, true);
        String nodePath = path + "/" + NetUtil.getHostAddress() + ":" + config.getLocalPort();
        zkClient.delete(nodePath);
        zkClient.createEphemeral(nodePath); //注意，如果服务中断 zk server 会在30s后才删除，除非自己手动删除
    }
}
