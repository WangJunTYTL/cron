package com.peaceful.cron.server.modal;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by wangjun38 on 2018/5/5.
 */
@Mapper
public interface CronDispatchMapper {

    @Insert("insert into cron_dispatch (`name`,`status`,`dispatch_time`) values (#{name},#{status},#{dispatchTime})")
    int insert(CronDispatch cronDispatch);

    @Select("select * from cron_dispatch where `id` = #{id}")
    CronDispatch selectById(@Param("id") long id);

    List<CronDispatch> select(CronDispatch cronDispatch);

    int update(CronDispatch cronDispatch);
}
