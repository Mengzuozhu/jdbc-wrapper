package com.github.mengzz.jdbc.wrapper.core;

import com.github.mengzz.jdbc.wrapper.wrapper.TableWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.sql.*;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Sql supporter.
 *
 * @author mengzz
 */
public class SqlSupporter {
    private static final int FIRST = 1;
    private final Map<Class<?>, String> tableNames = new ConcurrentReferenceHashMap<>();

    private final RelationalMappingContext context;

    public SqlSupporter(RelationalMappingContext context) {
        this.context = context;
    }

    /**
     * Of.
     *
     * @param context the context
     * @return the sql supporter
     */
    public static SqlSupporter of(RelationalMappingContext context) {
        return new SqlSupporter(context);
    }

    /**
     * Table.
     *
     * @param type the type
     * @return the table
     */
    public Table table(Class<?> type) {
        String tableName = tableNames.computeIfAbsent(type,
                k -> TableWrapper.getTableName(getRequiredPersistentEntity(type)));
        return Table.create(tableName);
    }

    /**
     * Build select.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the select
     */
    public <T> Select buildSelect(Condition where, Class<T> type) {
        SelectFromAndJoin fromAndJoin = buildSelectFromAndJoin(type);
        return fromAndJoin
                .where(where)
                .build();
    }

    /**
     * Build select first.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the select
     */
    public <T> Select buildSelectFirst(Condition where, Class<T> type) {
        SelectFromAndJoin fromAndJoin = buildSelectFromAndJoin(type);
        return fromAndJoin
                .limit(FIRST)
                .where(where)
                .build();
    }

    /**
     * Build select from and join.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @return the select from and join
     */
    public <T> SelectFromAndJoin buildSelectFromAndJoin(Class<T> type) {
        Table table = table(type);
        return StatementBuilder.select()
                .select(table.columns(TableWrapper.getAllCamelFieldNames(type)))
                .from(table);
    }

    /**
     * Page select.
     *
     * @param <T>      the type parameter
     * @param where    the where
     * @param pageable the pageable
     * @param type     the type
     * @return the select
     */
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

    /**
     * Count select.
     *
     * @param where the where
     * @param type  the type
     * @return the select
     */
    public Select countSelect(Condition where, Class<?> type) {
        Table table = table(type);
        return StatementBuilder.select()
                .select(Functions.count(Expressions.asterisk()))
                .from(table)
                .where(where)
                .build();
    }

    /**
     * Build delete.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the delete
     */
    public <T> Delete buildDelete(Condition where, Class<T> type) {
        Table table = table(type);
        return Delete.builder()
                .from(table)
                .where(where)
                .build();
    }

    /**
     * Gets required persistent entity.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @return the required persistent entity
     */
    @SuppressWarnings("unchecked")
    public <T> RelationalPersistentEntity<T> getRequiredPersistentEntity(Class<T> type) {
        return (RelationalPersistentEntity<T>) context.getRequiredPersistentEntity(type);
    }

    /**
     * Extract order by fields.
     *
     * @param <T>  the type parameter
     * @param sort the sort
     * @param type the type
     * @return the list
     */
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
