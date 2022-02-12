package com.github.mengzz.jdbc.wrapper.interceptor;

import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Visitable;

/**
 * @author mengzz
 **/
public class ConditionInterceptor extends AbstractInterceptor<Condition> {
    private Condition condition;

    public static ConditionInterceptor of() {
        return new ConditionInterceptor();
    }

    public static ConditionInterceptor visit(Visitable visitable) {
        ConditionInterceptor interceptor = new ConditionInterceptor();
        visitable.visit(interceptor);
        return interceptor;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public Class<Condition> getDomainType() {
        return Condition.class;
    }

    @Override
    public void intercept(Condition cond) {
        // intercept first
        if (condition == null) {
            condition = cond;
        }
    }
}
