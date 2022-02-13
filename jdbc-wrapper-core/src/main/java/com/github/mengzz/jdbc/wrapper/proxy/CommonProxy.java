package com.github.mengzz.jdbc.wrapper.proxy;

import com.github.mengzz.jdbc.wrapper.visitor.ConditionVisitor;
import com.github.mengzz.jdbc.wrapper.visitor.SqlVisitor;
import org.springframework.data.relational.core.sql.*;
import org.springframework.lang.Nullable;

/**
 * @author mengzz
 **/
public class CommonProxy implements SqlProxy {
    protected Condition condition;
    @Nullable
    protected Where where;
    protected From from;

    public CommonProxy(Visitable visitable) {
        SqlVisitor visitor = SqlVisitor.visit(visitable);
        where = visitor.getWhere();
        from = visitor.getFrom();
        condition = ConditionVisitor.visit(where).getCondition();
    }

    @Override
    public Condition getWhere() {
        return condition;
    }

    @Override
    public void setWhere(Condition condition) {
        this.condition = condition;
        Select select = Select.builder()
                .select(Expressions.asterisk())
                .from(from.getTables())
                .where(condition)
                .build();
        where = SqlVisitor.visit(select).getWhere();
    }

    protected void visitIfNotNull(@Nullable Visitable visitable, Visitor visitor) {

        if (visitable != null) {
            visitable.visit(visitor);
        }
    }

}
