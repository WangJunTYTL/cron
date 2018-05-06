CREATE TABLE cron_job(
  `id` int AUTO_INCREMENT PRIMARY KEY ,
  `name` VARCHAR(50) not NULL UNIQUE ,
  `cron_expression` VARCHAR(50) not NULL comment 'cron表达式',
  `status` int DEFAULT 0 comment '当前job状态',
  `next_execution_time` datetime comment '下次执行时间',
  `create_time` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'
);

CREATE TABLE cron_dispatch(
  `id` int AUTO_INCREMENT PRIMARY KEY ,
  `name` VARCHAR(50) not NULL ,
  `status` int DEFAULT 0 comment '本次执行结果状态',
  `retry_count` int  DEFAULT 0 comment '重试次数',
  `dispatch_time` datetime  comment '调度执行时间',
  `complete_time` datetime comment '执行完成时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'
);

