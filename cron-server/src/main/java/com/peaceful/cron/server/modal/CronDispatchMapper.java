package com.peaceful.cron.server.modal;


import com.peaceful.cron.server.dataobj.CronDispatchSearch;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Jun on 2018/5/5.
 */
@Mapper
public interface CronDispatchMapper {

    @Insert("insert into cron_dispatch (`name`,`status`,`plan_dispatch_time`) values (#{name},#{status},#{planDispatchTime})")
    int insert(CronDispatch cronDispatch);

    @Select("select * from cron_dispatch where `id` = #{id}")
    CronDispatch selectById(@Param("id") long id);

    @Select("select * from cron_dispatch where `id` = #{id} for update")
    CronDispatch lock(@Param("id") long id);

    List<CronDispatch> select(CronDispatchSearch search);

    int update(CronDispatch cronDispatch);
}
