package com.github.mengzz.jdbc.wrapper.proxy;

import com.github.mengzz.jdbc.wrapper.visitor.ConditionVisitor;
import com.github.mengzz.jdbc.wrapper.visitor.SelectVisitor;
import org.springframework.data.relational.core.sql.*;
import org.springframework.util.Assert;

import java.util.List;
import java.util.OptionalLong;

/**
 * @author mengzz
 **/
public class SelectProxy extends CommonProxy implements Select, SqlProxy {
    private final Select select;

    private List<Join> joins;
    private SelectList selectList;

    public SelectProxy(Select select) {
        this.select = select;
        from = select.getFrom();
        SelectVisitor visitor = SelectVisitor.visit(select);
        where = visitor.getWhere();
        joins = visitor.getJoins();
        selectList = visitor.getSelectList();
        condition = ConditionVisitor.visit(where).getCondition();
    }

    public static SelectProxy of(Select select) {
        return new SelectProxy(select);
    }

    @Override
    public From getFrom() {
        return select.getFrom();
    }

    @Override
    public List<OrderByField> getOrderBy() {
        return select.getOrderBy();
    }

    @Override
    public OptionalLong getLimit() {
        return select.getLimit();
    }

    @Override
    public OptionalLong getOffset() {
        return select.getOffset();
    }

    @Override
    public boolean isDistinct() {
        return select.isDistinct();
    }

    @Override
    public LockMode getLockMode() {
        return select.getLockMode();
    }

    @Override
    public void visit(Visitor visitor) {

        Assert.notNull(visitor, "Visitor must not be null!");

        visitor.enter(this);

        selectList.visit(visitor);
        getFrom().visit(visitor);
        joins.forEach(it -> it.visit(visitor));

        visitIfNotNull(where, visitor);

        getOrderBy().forEach(it -> it.visit(visitor));

        visitor.leave(this);
    }

}
