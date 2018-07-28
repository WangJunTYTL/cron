package com.peaceful.cron.client.core;

import com.peaceful.cron.client.util.CronClientConfig;
import com.peaceful.cron.client.util.NetUtil;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jun on 2018/5/17.
 */
public class CronNodeRegister {


    private static final ZkClient zkClient = new ZkClient("127.0.0.1:2181", 30000);
    private static Logger logger = LoggerFactory.getLogger(CronNodeRegister.class);

    private static Registry registry;

    private static class Registry implements Runnable {

        private CronClientConfig config;

        public Registry(CronClientConfig config) {
            this.config = config;
        }

        @Override
        public void run() {
            inner(config);
        }
    }

    static {
        zkClient.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {

            }

            @Override
            public void handleNewSession() {
                if (registry != null) {  // 如果session过期，这里应重新注册
                    registry.run();
                }
            }

            @Override
            public void handleSessionEstablishmentError(Throwable error) throws Exception {

            }
        });
    }

    public static synchronized void publish(CronClientConfig config) {
        if (registry == null) {
            registry = new Registry(config);
        }
        registry.run();
    }

    private static void inner(CronClientConfig config) {
        String path = "/cron/client/" + config.getClientId() + "/node";
        zkClient.createPersistent(path, true);
        String nodePath = path + "/" + NetUtil.getHostAddress() + ":" + config.getLocalPort();
        zkClient.delete(nodePath);
        zkClient.createEphemeral(nodePath); //注意，如果服务中断 zk server 会在30s后才删除，除非自己手动删除
        logger.info("cron client node registry suc!");
    }
}
