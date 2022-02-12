package com.github.mengzz.jdbc.wrapper.example.config;

import com.github.mengzz.jdbc.wrapper.interceptor.WhereInterceptor;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Where;

/**
 * 自定义拦截器，统一设置租户或公共字段等条件
 *
 * @author mengzz
 */
public class CustomWhereInterceptor extends WhereInterceptor {
    private static Condition record;

    public static Condition getRecord() {
        return record;
    }

    @Override
    public void intercept(Where segment) {
        super.intercept(segment);
        record = getCondition();
    }
}
