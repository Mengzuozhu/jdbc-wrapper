package com.github.mengzz.jdbc.wrapper.interceptor;

import org.springframework.data.relational.core.sql.Where;

/**
 * @author mengzz
 **/
public abstract class WhereInterceptor extends AbstractInterceptor<Where> {

    @Override
    public Class<Where> getGenericClass() {
        return Where.class;
    }
}
