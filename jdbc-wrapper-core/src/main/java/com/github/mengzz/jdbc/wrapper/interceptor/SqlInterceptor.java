package com.github.mengzz.jdbc.wrapper.interceptor;

import com.github.mengzz.jdbc.wrapper.proxy.SqlProxy;

/**
 * The interface Interceptor.
 *
 * @author mengzz
 */
public interface SqlInterceptor {

    /**
     * Intercept.
     *
     * @param sqlProxy the sqlProxy
     */
    void intercept(SqlProxy sqlProxy);

}
