package com.sibdev.file_reader_app.config.typehandler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@MappedJdbcTypes(JdbcType.OTHER)
@MappedTypes(Map.class)
@RequiredArgsConstructor
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
    private static final ObjectMapper OBJECT_MAPPER;
    private final Class<T> clazz;

    static {
        OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public void setNonNullParameter(
            final PreparedStatement ps,
            final int i,
            final T parameter,
            final JdbcType jdbcType
    ) throws SQLException {
        if (parameter == null) {
            ps.setObject(i, null);
            return;
        }

        final PGobject pgo = new PGobject();
        pgo.setType("jsonb");
        pgo.setValue(serialize(parameter));
        ps.setObject(i, pgo);
    }

    @Override
    public T getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        final String json = rs.getString(columnName);
        if (json == null) {
            return null;
        }

        return deserialize(json, clazz);
    }

    @Override
    public T getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        final String json = rs.getString(columnIndex);
        if (json == null) {
            return null;
        }
        return deserialize(json, clazz);
    }

    @Override
    public T getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        final String json = cs.getString(columnIndex);
        if (json == null) {
            return null;
        }
        return deserialize(json, clazz);
    }

    private String serialize(T object) throws SQLException {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new SQLException("Failed to serialize object: " + e.getMessage(), e);
        }
    }

    private T deserialize(String json, Class<T> clazz) throws SQLException {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (final JsonProcessingException e) {
            throw new SQLException("Failed to deserialize json: " + e.getMessage(), e);
        }
    }
}
