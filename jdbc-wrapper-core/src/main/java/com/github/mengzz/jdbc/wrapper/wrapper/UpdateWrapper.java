package com.github.mengzz.jdbc.wrapper.wrapper;

import org.springframework.data.relational.core.sql.Assignment;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.core.sql.Update;
import org.springframework.data.relational.core.sql.UpdateBuilder.UpdateWhereAndOr;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Update wrapper.
 *
 * @author mengzz
 */
public class UpdateWrapper extends CommonWrapper {
    private List<Assignment> assignments = new ArrayList<>();

    public UpdateWrapper(Table table) {
        super(table);
    }

    /**
     * Of.
     *
     * @param table the table
     * @return the update wrapper
     */
    public static UpdateWrapper of(Table table) {
        return new UpdateWrapper(table);
    }

    /**
     * Of.
     *
     * @param table the table
     * @return the update wrapper
     */
    public static UpdateWrapper of(String table) {
        return of(Table.create(table));
    }

    /**
     * Enable camel column.
     *
     * @param enable the enable
     * @return the condition wrapper
     */
    @Override
    public UpdateWrapper enableCamelColumn(boolean enable) {
        super.enableCamelColumn(enable);
        return this;
    }

    /**
     * Fluent set value.
     *
     * @param name  the name
     * @param value the value
     * @return the update wrapper
     */
    public UpdateWrapper set(String name, Object value) {
        assignments.add(setValue(name, value));
        return this;
    }

    /**
     * Where.
     *
     * @param condition the condition
     * @return the update builder . update where and or
     */
    public UpdateWhereAndOr where(Condition condition) {
        List<Assignment> assignments = collect();
        Assert.notEmpty(assignments, "Assignments must not be empty!");

        return Update.builder()
                .table(table)
                .set(assignments)
                .where(condition);
    }

    /**
     * Update where.
     *
     * @param condition the condition
     * @return the update
     */
    public Update updateWhere(Condition condition) {
        return where(condition)
                .build();
    }

    /**
     * Sets value.
     *
     * @param name  the name
     * @param value the value
     * @return the value
     */
    public Assignment setValue(String name, Object value) {
        return column(name).set(literalOf(value));
    }

    /**
     * Sets values.
     *
     * @param map the map
     * @return the values
     */
    public List<Assignment> setValues(Map<String, Object> map) {
        if (map == null) {
            return Collections.emptyList();
        }
        return map.entrySet()
                .stream()
                .map(entry -> setValue(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Collect.
     *
     * @return the list
     */
    public List<Assignment> collect() {
        return assignments;
    }

}
