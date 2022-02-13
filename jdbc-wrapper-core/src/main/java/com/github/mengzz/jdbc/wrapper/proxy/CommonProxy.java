package com.github.mengzz.jdbc.wrapper.proxy;

import com.github.mengzz.jdbc.wrapper.visitor.ConditionVisitor;
import com.github.mengzz.jdbc.wrapper.visitor.SqlVisitor;
import org.springframework.data.relational.core.sql.*;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author mengzz
 **/
public class CommonProxy implements SqlProxy {
    protected Condition condition;
    @Nullable
    protected Where where;
    protected From from;
    protected Table table;

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
    public void updateWhere(Condition condition) {
        this.condition = condition;
        List<Table> tables = getTables();
        Select select = Select.builder()
                .select(Expressions.asterisk())
                .from(tables)
                .where(condition)
                .build();
        where = SqlVisitor.visit(select).getWhere();
    }

    protected void visitIfNotNull(@Nullable Visitable visitable, Visitor visitor) {

        if (visitable != null) {
            visitable.visit(visitor);
        }
    }

    private List<Table> getTables() {
        List<Table> tables;
        if (from != null) {
            tables = from.getTables();
        } else {
            tables = Collections.singletonList(table);
        }
        return tables;
    }

}
