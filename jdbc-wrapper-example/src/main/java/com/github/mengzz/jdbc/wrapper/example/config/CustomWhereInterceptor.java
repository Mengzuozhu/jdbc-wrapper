package com.github.mengzz.jdbc.wrapper.example.config;

import com.github.mengzz.jdbc.wrapper.interceptor.WhereInterceptor;
import org.springframework.data.relational.core.sql.Where;

/**
 * 自定义拦截器，统一设置租户或公共字段等条件
 *
 * @author mengzz
 */
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
