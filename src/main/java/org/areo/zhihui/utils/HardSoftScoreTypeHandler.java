package org.areo.zhihui.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HardSoftScoreTypeHandler extends BaseTypeHandler<HardSoftScore> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    // 写入数据库
    public void setNonNullParameter(PreparedStatement ps, int i, HardSoftScore score, JdbcType jdbcType) throws SQLException {
        try {
//            String json = objectMapper.writeValueAsString(score);

            String json = String.format(
                    "{\"initScore\":%d,\"hardScore\":%d,\"softScore\":%d}",
                    score.initScore(),
                    score.hardScore(),
                    score.softScore()
            );
            ps.setString(i, json);
        } catch (Exception e) {
            throw new SQLException("写入数据库HardSoftScore失败", e);
        }
    }

    // 从数据库读取,三个重载方法

    @Override
    // 根据列名读取
    public HardSoftScore getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseScore(json);
    }

    @Override
    // 根据列索引读取
    public HardSoftScore getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return parseScore(json);
    }

    @Override
    // 从存储过程中读取
    public HardSoftScore getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return parseScore(json);
    }

    private HardSoftScore parseScore(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            // 仅提取需要的字段
            JsonNode node = objectMapper.readTree(json);
            int initScore = node.path("initScore").asInt();
            int hardScore = node.path("hardScore").asInt();
            int softScore = node.path("softScore").asInt();
            return HardSoftScore.of( hardScore, softScore);
        } catch (IOException e) {
            throw new RuntimeException("从数据库读取HardSoftScore失败", e);
        }
    }
}
