package com.github.mengzz.jdbc.wrapper.core;

import com.github.mengzz.jdbc.wrapper.interceptor.Interceptor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.*;
import org.springframework.lang.Nullable;

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
     * @throws DataAccessException the data access exception
     */
    Long count(Condition where, Class<?> type) throws DataAccessException;

    /**
     * Query.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the list
     * @throws DataAccessException the data access exception
     */
    <T> List<T> query(Condition where, Class<T> type) throws DataAccessException;

    /**
     * Query.
     *
     * @param <T>    the type parameter
     * @param select the select
     * @param type   the type
     * @return the list
     * @throws DataAccessException the data access exception
     */
    <T> List<T> query(Select select, Class<T> type) throws DataAccessException;

    /**
     * Page.
     *
     * @param <T>      the type parameter
     * @param where    the where
     * @param pageable the pageable
     * @param type     the type
     * @return the page
     * @throws DataAccessException the data access exception
     */
    <T> Page<T> page(Condition where, Pageable pageable, Class<T> type) throws DataAccessException;

    /**
     * Page.
     *
     * @param <T>      the type parameter
     * @param select   the select
     * @param pageable the pageable
     * @param type     the type
     * @return the page
     * @throws DataAccessException the data access exception
     */
    <T> Page<T> page(Select select, Pageable pageable, Class<T> type) throws DataAccessException;

    /**
     * Query first.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the t
     * @throws DataAccessException the data access exception
     */
    @Nullable
    <T> T queryFirst(Condition where, Class<T> type) throws DataAccessException;

    /**
     * Query first.
     *
     * @param <T>    the type parameter
     * @param select the select
     * @param type   the type
     * @return the t
     * @throws DataAccessException the data access exception
     */
    @Nullable
    <T> T queryFirst(Select select, Class<T> type) throws DataAccessException;

    /**
     * Query for object.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the t
     * @throws DataAccessException the data access exception
     */
    @Nullable
    <T> T queryForObject(Condition where, Class<T> type) throws DataAccessException;

    /**
     * Query for object.
     *
     * @param <T>    the type parameter
     * @param select the select
     * @param type   the type
     * @return the t
     * @throws DataAccessException the data access exception
     */
    @Nullable
    <T> T queryForObject(Select select, Class<T> type) throws DataAccessException;

    /**
     * Query for map.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the map
     * @throws DataAccessException the data access exception
     */
    @Nullable
    <T> Map<String, Object> queryForMap(Condition where, Class<T> type) throws DataAccessException;

    /**
     * Query for map.
     *
     * @param select the select
     * @return the map
     * @throws DataAccessException the data access exception
     */
    @Nullable
    Map<String, Object> queryForMap(Select select) throws DataAccessException;

    /**
     * Query for list.
     *
     * @param select the select
     * @return the list
     * @throws DataAccessException the data access exception
     */
    List<Map<String, Object>> queryForList(Select select) throws DataAccessException;

    /**
     * Update.
     *
     * @param update the update
     * @return the int
     * @throws DataAccessException the data access exception
     */
    int update(Update update) throws DataAccessException;

    /**
     * Delete.
     *
     * @param <T>   the type parameter
     * @param where the where
     * @param type  the type
     * @return the int
     * @throws DataAccessException the data access exception
     */
    <T> int delete(Condition where, Class<T> type) throws DataAccessException;

    /**
     * Delete.
     *
     * @param delete the delete
     * @return the int
     * @throws DataAccessException the data access exception
     */
    int delete(Delete delete) throws DataAccessException;

    /**
     * Sets interceptors.
     *
     * @param interceptors the interceptors
     * @throws DataAccessException the data access exception
     */
    void setInterceptors(List<Interceptor> interceptors) throws DataAccessException;
}
