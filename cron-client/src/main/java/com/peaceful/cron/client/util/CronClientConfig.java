package com.peaceful.cron.client.util;

import com.peaceful.cron.client.exception.CronException;
import com.peaceful.cron.client.exception.CronExceptionEnum;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Jun on 2018/5/12.
 */
public class CronClientConfig {

    private String serverAddress;
    private String clientId;
    private Integer localPort;
    private String zkAddress;

    private static final String CRON_SERVER_ADDRESS_KEY = "cron.server.address";
    private static final String CRON_CLIENT_ID_KEY = "cron.client.id";
    private static final String CRON_CLIENT_PORT_KEY = "cron.client.port";
    private static final String CRON_ZK_ADDRESS_KEY = "cron.server.zk.address";

    public void validate() {
        if (StringUtils.isBlank(serverAddress)) {
            throw new CronException(CronExceptionEnum.CRON_CLIENT_ERROR, "cron[serverAddress] config is empty");
        }
        if (clientId == null) {
            throw new CronException(CronExceptionEnum.CRON_CLIENT_ERROR, "cron[clientId] config is empty");
        }
        if (zkAddress == null) {
            throw new CronException(CronExceptionEnum.CRON_CLIENT_ERROR, "cron[zkAddress] config is empty");
        }
    }

    public CronClientConfig() {
        try {
            String serverAddress = System.getProperty(CRON_SERVER_ADDRESS_KEY);
            String clientId = System.getProperty(CRON_CLIENT_ID_KEY);
            String clientPort = System.getProperty(CRON_CLIENT_PORT_KEY);

            setServerAddress(serverAddress);
            setLocalPort(IntegerUtil.parseWithDefault(clientPort, 5656));
            setClientId(clientId);

            // merge resource properties
            ResourceBundle resource = ResourceBundle.getBundle("cron");
            if (StringUtils.isBlank(getServerAddress())) {
                setServerAddress(resource.getString(CRON_SERVER_ADDRESS_KEY));
            }
            if (getLocalPort() == null) {
                setLocalPort(IntegerUtil.parse(resource.getString(CRON_CLIENT_PORT_KEY)));
            }
            if (getClientId() == null) {
                setClientId(resource.getString(CRON_CLIENT_ID_KEY));
            }
            setZkAddress(resource.getString(CRON_ZK_ADDRESS_KEY));
        } catch (MissingResourceException e) {
            // ignore
        }
    }


    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getLocalPort() {
        return localPort;
    }

    public void setLocalPort(Integer localPort) {
        this.localPort = localPort;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }
}
