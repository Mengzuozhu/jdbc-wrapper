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
    @Nullable
    private Where where;
    private List<Join> joins = new ArrayList<>();
    private SelectList selectList;
    private From from;

    public SelectVisitor() {
    }

    public static SelectVisitor of() {
        return new SelectVisitor();
    }

    public static SelectVisitor visit(Visitable visitable) {
        SelectVisitor interceptor = of();
        visitable.visit(interceptor);
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

    public From getFrom() {
        return from;
    }

    @Override
    public void enter(Visitable segment) {
        if (segment instanceof Where) {
            where = (Where) segment;
        } else if (segment instanceof SelectList) {
            selectList = (SelectList) segment;
        } else if (segment instanceof Join) {
            joins.add((Join) segment);
        } else if (segment instanceof From) {
            from = (From) segment;
        }
    }
}
