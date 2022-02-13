package com.github.mengzz.jdbc.wrapper.proxy;

import com.github.mengzz.jdbc.wrapper.visitor.SelectVisitor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.sql.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.OptionalLong;

/**
 * @author mengzz
 **/
@Getter
@Setter
public class SelectProxy implements Select {
    private final Select select;
    private List<Join> joins;
    private SelectList selectList;
    private Where where;

    public SelectProxy(Select select) {
        this.select = select;
        SelectVisitor visitor = SelectVisitor.visit(select);
        where = visitor.getWhere();
        joins = visitor.getJoins();
        selectList = visitor.getSelectList();
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

    private void visitIfNotNull(@Nullable Visitable visitable, Visitor visitor) {

        if (visitable != null) {
            visitable.visit(visitor);
        }
    }
}
