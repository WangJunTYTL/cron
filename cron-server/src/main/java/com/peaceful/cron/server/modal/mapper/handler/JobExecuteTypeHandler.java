package com.peaceful.cron.server.modal.mapper.handler;


import com.peaceful.cron.server.modal.enums.DispatchStatus;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jun on 2018/5/5.
 */
public class JobExecuteTypeHandler extends BaseTypeHandler<DispatchStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, DispatchStatus status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, status.getCode());
    }

    @Override
    public DispatchStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int code = resultSet.getInt(s);
        return DispatchStatus.getByCode(code);
    }

    @Override
    public DispatchStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        return DispatchStatus.getByCode(code);
    }

    @Override
    public DispatchStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return DispatchStatus.getByCode(code);
    }
}

