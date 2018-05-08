package com.peaceful.cron.server.modal;

import com.peaceful.cron.server.dataobj.CronJobSearch;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Juna on 2018/5/5.
 */
@Mapper
public interface CronJobMapper {

    @Insert("insert into cron_job (`name`,`cron_expression`,`status`,`update_time`) values (#{name},#{cronExpression},#{status},now())")
    int insert(CronJob cronJob);

    @Select("select * from cron_job where `status` = #{status}")
    List<CronJob> selectListByStatus(@Param("status") JobStatus status);

    @Select("select * from cron_job")
    List<CronJob> selectList();

    @Select("select * from cron_job where `id` = #{id}")
    CronJob selectById(@Param("id") long id);

    @Select("select * from cron_job where `name` = #{name}")
    CronJob selectByName(@Param("name") String name);

    int update(CronJob cronJob);

    List<CronJob> select(CronJobSearch search);
}
