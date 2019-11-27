package com.example.mybatisplus.common.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mybatisplus.entity.Content;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({Content.class})
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
    private Class<T> clazz;

    public JsonTypeHandler(Class<T> clazz) {
        this.clazz=clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, T t, JdbcType jdbcType) throws SQLException {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        jsonObject.setValue(JSON.toJSONString(t));
        preparedStatement.setObject(i, jsonObject);
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return JSONObject.parseObject(resultSet.getString(s),clazz);
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return JSONObject.parseObject(resultSet.getString(i),clazz);
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JSONObject.parseObject(callableStatement.getString(i),clazz);
    }
}
