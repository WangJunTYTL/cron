package com.peaceful.cron.server.modal.mapper;

import com.peaceful.cron.server.modal.ServiceRegistry;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Jun on 2018/5/26.
 */
@Mapper
public interface ServiceRegistryMapper {

    @Insert("insert into cron_service_registry (`name`,`comment`,`status`) values (#{name},#{comment},1)")
    int insert(ServiceRegistry serviceRegistry);

    @Select("select * from cron_service_registry where name = #{name}")
    ServiceRegistry selectByName(String name);

    @Select("select * from cron_service_registry where id = #{id}")
    ServiceRegistry selectById(long id);

    @Select("select * from cron_service_registry")
    List<ServiceRegistry> selectAll();
}
