package com.github.mengzz.jdbc.wrapper.proxy;

import org.springframework.data.relational.core.sql.Condition;

/**
 * The interface Sql proxy.
 *
 * @author mengzz
 */
public interface SqlProxy {
    /**
     * Gets where.
     *
     * @return the where
     */
    Condition getWhere();

    /**
     * Sets where.
     *
     * @param condition the condition
     */
    void setWhere(Condition condition);

}
