CREATE TABLE cron_job(
  `id` bigint AUTO_INCREMENT PRIMARY KEY ,
  `service_id` bigint NOT NULL comment '服务标识id',
  `name` VARCHAR(50) not NULL UNIQUE ,
  `cron_expression` VARCHAR(50) not NULL comment 'cron表达式',
  `status` int DEFAULT 0 comment '当前job状态',
  `next_execution_time` datetime comment '下次执行时间',
  `create_time` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'
);

CREATE TABLE cron_dispatch(
  `id` int AUTO_INCREMENT PRIMARY KEY ,
  `service_id` bigint NOT NULL comment '服务标识id',
  `name` VARCHAR(50) not NULL ,
  `status` int DEFAULT 0 comment '本次执行结果状态',
  `ip_port`  VARCHAR(20) DEFAULT '' comment '执行机器',
  `ack_message` VARCHAR(20) DEFAULT '' comment '返回执行结果',
  `cost` bigint DEFAULT 0 comment '执行时间',
  `retry_count` int  DEFAULT 0 comment '重试次数',
  `plan_dispatch_time` datetime  comment '预计调度时间',
  `real_dispatch_time` datetime  comment '实际调度时间',
  `real_execution_time` datetime  comment '任务实际执行时间',
  `complete_time` datetime comment '执行完成时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   UNIQUE KEY `unique_job_dispatch` (`name`,`plan_dispatch_time`)
);

CREATE TABLE cron_service_registry(
  `id` int AUTO_INCREMENT PRIMARY KEY ,
  `name` VARCHAR(100) not NULL UNIQUE comment '服务唯一标识' ,
  `comment` VARCHAR(200) not NULL comment '服务概述',
  `status` int DEFAULT 0 comment '服务接入状态',
  `create_time` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'
);

