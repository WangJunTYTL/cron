cron
---------
任务调度控制器



## 调度执行模式
* 支持：scheduleAtFixedRate
* 不支持：scheduleWithFixedDelay

##不支持特性
1. 删除任务或更新时，是否需要立即停止业务模块的正在执行的任务
2. Job添加后，不允许更新名字

## TODO
1. 支持任务删除
2. 调度指令发送


cron 表达式解析
------
https://github.com/jmrozanec/cron-utils

在线表达式生成
----------------
http://cron.qqe2.com