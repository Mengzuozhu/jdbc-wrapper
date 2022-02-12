package com.github.mengzz.jdbc.wrapper.interceptor;

import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Visitable;
import org.springframework.data.relational.core.sql.Where;

/**
 * @author mengzz
 **/
public class WhereInterceptor extends AbstractInterceptor<Where> {
    private Condition condition;
    private Where where;

    public static WhereInterceptor visit(Visitable visitable) {
        WhereInterceptor interceptor = new WhereInterceptor();
        visitable.visit(interceptor);
        return interceptor;
    }

    public Where getWhere() {
        return where;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public Class<Where> getDomainType() {
        return Where.class;
    }

    @Override
    public void intercept(Where where) {
        this.where = where;
        // 探测where对应的Condition
        condition = ConditionInterceptor.visit(where).getCondition();
    }
}
