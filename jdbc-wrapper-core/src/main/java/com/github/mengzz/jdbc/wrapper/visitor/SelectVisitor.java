package com.github.mengzz.jdbc.wrapper.visitor;

import org.springframework.data.relational.core.sql.*;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Select interceptor.
 *
 * @author mengzz
 */
public class SelectVisitor implements Visitor {
    private Select select;
    @Nullable
    private Where where;
    private List<Join> joins = new ArrayList<>();
    private SelectList selectList;

    public SelectVisitor(Select select) {
        this.select = select;
    }

    public static SelectVisitor of(Select select) {
        return new SelectVisitor(select);
    }

    public static SelectVisitor visit(Select select) {
        SelectVisitor interceptor = of(select);
        select.visit(interceptor);
        return interceptor;
    }

    public SelectList getSelectList() {
        return selectList;
    }

    public List<Join> getJoins() {
        return joins;
    }

    @Nullable
    public Where getWhere() {
        return where;
    }

    @Override
    public void enter(Visitable segment) {
        if (segment instanceof Where) {
            where = (Where) segment;
        } else if (segment instanceof SelectList) {
            selectList = (SelectList) segment;
        } else if (segment instanceof Join) {
            joins.add((Join) segment);
        }
    }

}
