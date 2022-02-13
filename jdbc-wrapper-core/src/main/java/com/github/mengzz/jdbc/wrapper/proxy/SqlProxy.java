package com.github.mengzz.jdbc.wrapper.proxy;

import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.From;
import org.springframework.data.relational.core.sql.Table;

import java.util.List;

/**
 * The interface Sql proxy.
 *
 * @author mengzz
 */
public interface SqlProxy {
    /**
     * Gets from.
     *
     * @return the from
     */
    From getFrom();

    /**
     * Gets table.
     *
     * @return the table
     */
    Table getTable();

    /**
     * Gets where.
     *
     * @return the where
     */
    Condition getWhere();

    /**
     * Gets tables.
     *
     * @return the tables
     */
    List<Table> getTables();

    /**
     * Update where.
     *
     * @param condition the condition
     */
    void updateWhere(Condition condition);
}
