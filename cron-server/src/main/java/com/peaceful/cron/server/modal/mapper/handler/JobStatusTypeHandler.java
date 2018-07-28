package com.peaceful.cron.server.modal.mapper.handler;


import com.peaceful.cron.server.modal.enums.JobStatus;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jun on 2018/5/5.
 */
public class JobStatusTypeHandler extends BaseTypeHandler<JobStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JobStatus jobStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, jobStatus.getCode());
    }

    @Override
    public JobStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int code = resultSet.getInt(s);
        return JobStatus.getByCode(code);
    }

    @Override
    public JobStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        return JobStatus.getByCode(code);
    }

    @Override
    public JobStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return JobStatus.getByCode(code);
    }
}

