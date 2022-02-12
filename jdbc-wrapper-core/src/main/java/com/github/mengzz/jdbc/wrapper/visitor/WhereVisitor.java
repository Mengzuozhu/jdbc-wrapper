package com.github.mengzz.jdbc.wrapper.visitor;

import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Visitable;
import org.springframework.data.relational.core.sql.Where;

/**
 * @author mengzz
 **/
public class WhereVisitor extends AbstractVisitor<Where> {
    private Condition condition;
    private Where where;

    public static WhereVisitor visit(Visitable visitable) {
        WhereVisitor interceptor = new WhereVisitor();
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
        condition = ConditionVisitor.visit(where).getCondition();
    }
}
