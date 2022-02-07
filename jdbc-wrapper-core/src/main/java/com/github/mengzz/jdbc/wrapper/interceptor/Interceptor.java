package com.github.mengzz.jdbc.wrapper.interceptor;

import org.springframework.data.relational.core.sql.Visitable;
import org.springframework.data.relational.core.sql.Visitor;

/**
 * The interface Interceptor.
 *
 * @author mengzz
 */
public interface Interceptor extends Visitor {
    /**
     * Enter.
     *
     * @param segment the segment
     */
    @Override
    default void enter(Visitable segment) {
        intercept(segment);
    }

    /**
     * Visit.
     *
     * @param segment the segment
     */
    void intercept(Visitable segment);

}
