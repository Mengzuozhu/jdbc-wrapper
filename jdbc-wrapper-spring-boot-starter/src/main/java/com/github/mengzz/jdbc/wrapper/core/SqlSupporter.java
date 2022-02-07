package com.github.mengzz.jdbc.wrapper.core;

import com.github.mengzz.jdbc.wrapper.wrapper.TableWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.sql.*;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengzz
 **/
public class SqlSupporter {
    private final Map<Class<?>, String> tableNames = new ConcurrentReferenceHashMap<>();

    private final RelationalMappingContext context;

    public SqlSupporter(RelationalMappingContext context) {
        this.context = context;
    }

    public static SqlSupporter of(RelationalMappingContext context) {
        return new SqlSupporter(context);
    }

    public Table table(Class<?> type) {
        String tableName = tableNames.computeIfAbsent(type,
                k -> TableWrapper.getTableName(getRequiredPersistentEntity(type)));
        return Table.create(tableName);
    }

    public <T> Select buildSelect(Condition where, Class<T> type) {
        Table table = table(type);
        return StatementBuilder.select()
                .select(table.columns(TableWrapper.getAllCamelFieldNames(type)))
                .from(table)
                .where(where)
                .build();
    }

    public <T> Select pageSelect(Condition where, Pageable pageable, Class<T> type) {
        Table table = table(type);
        return StatementBuilder.select()
                .select(table.columns(TableWrapper.getAllCamelFieldNames(type)))
                .from(table)
                .limitOffset(pageable.getPageSize(), pageable.getOffset())
                .where(where)
                .orderBy(extractOrderByFields(pageable.getSort(), type))
                .build();
    }

    public Select countSelect(Condition where, Class<?> type) {
        Table table = table(type);
        return StatementBuilder.select()
                .select(Functions.count(Expressions.asterisk()))
                .from(table)
                .where(where)
                .build();
    }

    public <T> Delete buildDelete(Condition where, Class<T> type) {
        Table table = table(type);
        return Delete.builder()
                .from(table)
                .where(where)
                .build();
    }

    @SuppressWarnings("unchecked")
    public <T> RelationalPersistentEntity<T> getRequiredPersistentEntity(Class<T> type) {
        return (RelationalPersistentEntity<T>) context.getRequiredPersistentEntity(type);
    }

    private <T> List<OrderByField> extractOrderByFields(Sort sort, Class<T> type) {
        Table table = table(type);
        RelationalPersistentEntity<T> entity = getRequiredPersistentEntity(type);
        return sort.stream()
                .map(order -> {
                    SqlIdentifier columnName =
                            entity.getRequiredPersistentProperty(order.getProperty()).getColumnName();
                    Column column = table.column(columnName);
                    return OrderByField.from(column, order.getDirection());
                })
                .collect(Collectors.toList());
    }
}
