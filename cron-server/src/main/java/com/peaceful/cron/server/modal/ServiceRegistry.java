package com.peaceful.cron.server.modal;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by Jun on 2018/5/26.
 */
@Data
public class ServiceRegistry {

    private long id;
    private String name;
    private String comment;
    private Timestamp createTime;
    private Timestamp updateTime;
}
