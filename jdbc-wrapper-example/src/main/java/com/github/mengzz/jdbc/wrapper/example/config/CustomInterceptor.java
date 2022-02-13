package com.github.mengzz.jdbc.wrapper.example.config;

import com.github.mengzz.jdbc.wrapper.example.model.User;
import com.github.mengzz.jdbc.wrapper.interceptor.SqlInterceptor;
import com.github.mengzz.jdbc.wrapper.proxy.SqlProxy;
import com.github.mengzz.jdbc.wrapper.wrapper.ConditionWrapper;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

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
        Optional.ofNullable(sqlProxy)
                .map(SqlProxy::getTables)
                .map(CollectionUtils::firstElement)
                .ifPresent(table -> {
                    Condition where = sqlProxy.getWhere();
                    ConditionWrapper tenantCond = ConditionWrapper.of(table)
                            .andEq(User.Fields.tenant, "test");
                    where = where.and(tenantCond);
                    record = where;
                    sqlProxy.updateWhere(where);
                });
    }
}
