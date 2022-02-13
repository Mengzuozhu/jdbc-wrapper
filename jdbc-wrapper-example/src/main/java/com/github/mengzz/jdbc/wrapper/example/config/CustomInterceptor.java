package com.github.mengzz.jdbc.wrapper.example.config;

import com.github.mengzz.jdbc.wrapper.interceptor.SqlInterceptor;
import com.github.mengzz.jdbc.wrapper.proxy.SqlProxy;
import org.springframework.data.relational.core.sql.Condition;

/**
 * 自定义拦截器，统一设置租户或公共字段等条件
 *
 * @author mengzz
 */
public class CustomInterceptor implements SqlInterceptor {
    private static Condition record;

    public static Condition getRecord() {
        return record;
    }

    @Override
    public void intercept(SqlProxy sqlProxy) {
        record = sqlProxy.getWhere();
        sqlProxy.setWhere(record);
    }
}
