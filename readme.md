Cron
-------

分布式任务调度中间件，支持下列特性：

1. 调度规则支持Cron表达式，类似于Linux系统上的cron服务的任务配置
1. 采用CS架构，可用于多种编程语言下的项目任务调度，如Java、Shell、Python等
1. 易用的Web管理控制台，方便Job配置、管理、查看
1. 提供实用的监控报表，可通过Web实时了解任务调度情况
1. 为公司集群分布式生成环境下的任务调度，提供统一通用的任务调度解决方案 


<img src="./icon.png" width = "200"/>

Quick Start
--------
## 一、启动Cron服务端
1、修改application.yml,配置MySql数据库连接，并执行cron.sql创建必要的表

2、执行StartCronServer中的main函数启动服务端

3、访问127.0.0.1:8787

## 二、编写你自己的Job
1、编写自己的Job，需要继承Runnable接口

```
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
```

2、添加Job实例

```$xslt
CronScheduler.add("myJob", new JobMock());
```
## 三、在服务端配置服务调度规则
访问http://127.0.0.1:8787/cron 配置Job每分钟执行一次
	
	 Job名称：myJob
	 Cron表达式：0 0/1 * * * ? 
 
