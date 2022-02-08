package com.github.mengzz.jdbc.wrapper.core;

import com.github.mengzz.jdbc.wrapper.interceptor.Interceptor;
import com.github.mengzz.jdbc.wrapper.interceptor.SelectInterceptor;
import com.github.mengzz.jdbc.wrapper.wrapper.RendererWrapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.sql.*;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;
import java.util.Map;

/**
 * The type Jdbc wrapper.
 *
 * @author mengzz
 */
public class JdbcWrapperImpl implements JdbcWrapper {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final JdbcConverter converter;
    private final RendererWrapper rendererWrapper;
    private final SqlSupporter sqlSupporter;

    public JdbcWrapperImpl(NamedParameterJdbcOperations jdbcOperations,
                           RelationalMappingContext context, JdbcConverter converter, Dialect dialect) {
        this.jdbcOperations = jdbcOperations;
        this.converter = converter;
        rendererWrapper = RendererWrapper.create(dialect);
        sqlSupporter = SqlSupporter.of(context);
    }

    @Override
    public Table table(Class<?> type) {
        return sqlSupporter.table(type);
    }

    @Override
    public Long count(Condition where, Class<?> type) {
        Select count = sqlSupporter.countSelect(where, type);
        return getJdbcOperations().queryForObject(render(count), Long.class);
    }

    @Override
    public <T> List<T> query(Condition where, Class<T> type) {
        Select select = sqlSupporter.buildSelect(where, type);
        return query(select, type);
    }

    @Override
    public <T> List<T> query(Select select, Class<T> type) {
        EntityRowMapper<T> entityRowMapper = getEntityRowMapper(type);
        return getJdbcOperations().query(render(select), entityRowMapper);
    }

    @Override
    public <T> Page<T> page(Condition where, Pageable pageable, Class<T> type) {
        Select select = sqlSupporter.pageSelect(where, pageable, type);
        return page(select, pageable, type);
    }

    @Override
    public <T> Page<T> page(Select select, Pageable pageable, Class<T> type) {
        EntityRowMapper<T> entityRowMapper = getEntityRowMapper(type);
        Where where = SelectInterceptor.visit(select).getWhere();
        List<T> content = getJdbcOperations().query(render(select), entityRowMapper);
        return PageableExecutionUtils.getPage(content, pageable, () -> pageCount(where, type));
    }

    @Override
    public <T> T queryForObject(Condition where, Class<T> type) {
        Select select = sqlSupporter.buildSelect(where, type);
        return queryForObject(select, type);
    }

    @Override
    public <T> T queryForObject(Select select, Class<T> type) {
        EntityRowMapper<T> entityRowMapper = getEntityRowMapper(type);
        try {
            return getJdbcOperations().queryForObject(render(select), entityRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public <T> Map<String, Object> queryForMap(Condition where, Class<T> type) {
        Select select = sqlSupporter.buildSelect(where, type);
        return queryForMap(select);
    }

    @Override
    public Map<String, Object> queryForMap(Select select) {
        try {
            return getJdbcOperations().queryForMap(render(select));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int update(Update update) {
        return getJdbcOperations().update(render(update));
    }

    @Override
    public <T> int delete(Condition where, Class<T> type) {
        Delete delete = sqlSupporter.buildDelete(where, type);
        return delete(delete);
    }

    @Override
    public int delete(Delete delete) {
        return getJdbcOperations().update(render(delete));
    }

    @Override
    public void setInterceptors(List<Interceptor> interceptors) {
        rendererWrapper.setInterceptors(interceptors);
    }

    private <T> Long pageCount(Where where, Class<T> type) {
        Select select = StatementBuilder.select()
                .select(Functions.count(Expressions.asterisk()))
                .from(table(type))
                .build();
        String render = render(select);
        if (where != null) {
            render = render + " " + where.toString();
        }
        return getJdbcOperations().queryForObject(render, Long.class);
    }

    private String render(Segment segment) {
        return rendererWrapper.render(segment);
    }

    private JdbcOperations getJdbcOperations() {
        return jdbcOperations.getJdbcOperations();
    }

    private <T> EntityRowMapper<T> getEntityRowMapper(Class<T> type) {
        return new EntityRowMapper<>(sqlSupporter.getRequiredPersistentEntity(type), converter);
    }

}
