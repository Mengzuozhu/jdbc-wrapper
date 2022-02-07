package com.github.mengzz.jdbc.wrapper.example.config;

import com.github.mengzz.jdbc.wrapper.interceptor.WhereInterceptor;
import org.springframework.data.relational.core.sql.Where;

/**
 * @author mengzz
 **/
public class CustomWhereInterceptor extends WhereInterceptor {
    private static Where record;

    public static Where getRecord() {
        return record;
    }

    @Override
    public void intercept(Where segment) {
        record = segment;
    }
}
