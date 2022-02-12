package com.github.mengzz.jdbc.wrapper.visitor;

import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Visitable;

/**
 * @author mengzz
 **/
public class ConditionVisitor extends AbstractVisitor<Condition> {
    private Condition condition;

    public static ConditionVisitor of() {
        return new ConditionVisitor();
    }

    public static ConditionVisitor visit(Visitable visitable) {
        ConditionVisitor interceptor = new ConditionVisitor();
        visitable.visit(interceptor);
        return interceptor;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void intercept(Condition cond) {
        // intercept first
        if (condition == null) {
            condition = cond;
        }
    }
}
