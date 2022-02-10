package com.github.mengzz.jdbc.wrapper.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.*;

import java.util.List;
import java.util.Map;

/**
 * The interface Jdbc wrapper proxy.
 *
 * @param <T> the type parameter
 * @author mengzz
 */
public interface JdbcWrapperProxy<T> {

    /**
     * Gets jdbc wrapper.
     *
     * @return the jdbc wrapper
     */
    JdbcWrapper getJdbcWrapper();

    /**
     * Gets domain type.
     *
     * @return the genetic class
     */
    Class<T> getDomainType();

    /**
     * Table.
     *
     * @return the table
     */
    default Table table() {
        return getJdbcWrapper().table(getDomainType());
    }

    /**
     * Count.
     *
     * @param where the where
     * @return the long
     */
    default Long count(Condition where) {
        return getJdbcWrapper().count(where, getDomainType());
    }

    /**
     * Query.
     *
     * @param where the where
     * @return the list
     */
    default List<T> query(Condition where) {
        return getJdbcWrapper().query(where, getDomainType());
    }

    /**
     * Query.
     *
     * @param select the select
     * @return the list
     */
    default List<T> query(Select select) {
        return getJdbcWrapper().query(select, getDomainType());
    }

    /**
     * Page.
     *
     * @param where    the where
     * @param pageable the pageable
     * @return the page
     */
    default Page<T> page(Condition where, Pageable pageable) {
        return getJdbcWrapper().page(where, pageable, getDomainType());
    }

    /**
     * Page.
     *
     * @param select   the select
     * @param pageable the pageable
     * @return the page
     */
    default Page<T> page(Select select, Pageable pageable) {
        return getJdbcWrapper().page(select, pageable, getDomainType());
    }

    /**
     * Query first.
     *
     * @param where the where
     * @return the t
     */
    default T queryFirst(Condition where) {
        return getJdbcWrapper().queryFirst(where, getDomainType());
    }

    /**
     * Query first.
     *
     * @param select the select
     * @return the t
     */
    default T queryFirst(Select select) {
        return getJdbcWrapper().queryFirst(select, getDomainType());
    }

    /**
     * Query for object.
     *
     * @param where the where
     * @return the t
     */
    default T queryForObject(Condition where) {
        return getJdbcWrapper().queryForObject(where, getDomainType());
    }

    /**
     * Query for object.
     *
     * @param select the select
     * @return the t
     */
    default T queryForObject(Select select) {
        return getJdbcWrapper().queryForObject(select, getDomainType());
    }

    /**
     * Query for map.
     *
     * @param where the where
     * @return the map
     */
    default Map<String, Object> queryForMap(Condition where) {
        return getJdbcWrapper().queryForMap(where, getDomainType());
    }

    /**
     * Query for map.
     *
     * @param select the select
     * @return the map
     */
    default Map<String, Object> queryForMap(Select select) {
        return getJdbcWrapper().queryForMap(select);
    }

    /**
     * Query for list.
     *
     * @param select the select
     * @return the list
     */
    default List<Map<String, Object>> queryForList(Select select) {
        return getJdbcWrapper().queryForList(select);
    }

    /**
     * Delete.
     *
     * @param where the where
     * @return the int
     */
    default int delete(Condition where) {
        return getJdbcWrapper().delete(where, getDomainType());
    }

    /**
     * Delete.
     *
     * @param delete the delete
     * @return the int
     */
    default int delete(Delete delete) {
        return getJdbcWrapper().delete(delete);
    }

    /**
     * Update.
     *
     * @param update the update
     * @return the int
     */
    default int update(Update update) {
        return getJdbcWrapper().update(update);
    }

}
