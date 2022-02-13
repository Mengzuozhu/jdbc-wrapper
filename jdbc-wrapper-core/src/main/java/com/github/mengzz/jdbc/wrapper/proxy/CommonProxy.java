package com.github.mengzz.jdbc.wrapper.proxy;

import com.github.mengzz.jdbc.wrapper.visitor.SelectVisitor;
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
        where = SelectVisitor.visit(select).getWhere();
    }

    protected void visitIfNotNull(@Nullable Visitable visitable, Visitor visitor) {

        if (visitable != null) {
            visitable.visit(visitor);
        }
    }

}
