package com.github.mengzz.jdbc.wrapper.core;

import com.github.mengzz.jdbc.wrapper.interceptor.Interceptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.*;

import java.util.List;
import java.util.Map;

/**
 * The interface Jdbc wrapper.
 *
 * @author mengzz
 */
public interface JdbcWrapper {

    /**
     * Table.
     *
     * @param type the type
     * @return the table
     */
    Table table(Class<?> type);

    /**
     * Count.
     *
     * @param where the where
     * @param type  the type
     * @return the long
     */
    Long count(Condition where, Class<?> type);

    /**
     * Query.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the list
     */
    <T> List<T> query(Condition where, Class<T> type);

    /**
     * Query.
     *
     * @param <T>    the type parameter
     * @param select the select
     * @param type   the type
     * @return the list
     */
    <T> List<T> query(Select select, Class<T> type);

    /**
     * Page.
     *
     * @param <T>      the type parameter
     * @param where    the where
     * @param pageable the pageable
     * @param type     the type
     * @return the page
     */
    <T> Page<T> page(Condition where, Pageable pageable, Class<T> type);

    /**
     * Page.
     *
     * @param <T>      the type parameter
     * @param select   the select
     * @param pageable the pageable
     * @param type     the type
     * @return the page
     */
    <T> Page<T> page(Select select, Pageable pageable, Class<T> type);

    /**
     * Query first.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the t
     */
    <T> T queryFirst(Condition where, Class<T> type);

    /**
     * Query first.
     *
     * @param <T>    the type parameter
     * @param select the select
     * @param type   the type
     * @return the t
     */
    <T> T queryFirst(Select select, Class<T> type);

    /**
     * Query for object.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the t
     */
    <T> T queryForObject(Condition where, Class<T> type);

    /**
     * Query for object.
     *
     * @param <T>    the type parameter
     * @param select the select
     * @param type   the type
     * @return the t
     */
    <T> T queryForObject(Select select, Class<T> type);

    /**
     * Query for map.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the map
     */
    <T> Map<String, Object> queryForMap(Condition where, Class<T> type);

    /**
     * Query for map.
     *
     * @param select the select
     * @return the map
     */
    Map<String, Object> queryForMap(Select select);

    /**
     * Query for list.
     *
     * @param select the select
     * @return the list
     */
    List<Map<String, Object>> queryForList(Select select);

    /**
     * Update.
     *
     * @param update the update
     * @return the int
     */
    int update(Update update);

    /**
     * Delete.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the int
     */
    <T> int delete(Condition where, Class<T> type);

    /**
     * Delete.
     *
     * @param delete the delete
     * @return the int
     */
    int delete(Delete delete);

    /**
     * Sets interceptors.
     *
     * @param interceptors the interceptors
     */
    void setInterceptors(List<Interceptor> interceptors);
}
