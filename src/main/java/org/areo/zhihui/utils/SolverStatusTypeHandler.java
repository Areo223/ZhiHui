package org.areo.zhihui.utils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.optaplanner.core.api.solver.SolverStatus;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SolverStatusTypeHandler extends BaseTypeHandler<SolverStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SolverStatus status, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, status.name());
    }

    @Override
    public SolverStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : SolverStatus.valueOf(value);
    }

    @Override
    public SolverStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : SolverStatus.valueOf(value);
    }

    @Override
    public SolverStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : SolverStatus.valueOf(value);
    }
}