package com.github.mengzz.jdbc.wrapper.interceptor;

import org.springframework.data.relational.core.sql.Segment;

/**
 * The interface Interceptor.
 *
 * @author mengzz
 */
public interface Interceptor {

    /**
     * Intercept.
     *
     * @param segment the segment
     * @return the segment
     */
    Segment intercept(Segment segment);

}
