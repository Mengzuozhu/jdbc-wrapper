package com.github.mengzz.jdbc.wrapper.visitor;

import org.springframework.data.relational.core.sql.SelectList;
import org.springframework.data.relational.core.sql.Visitable;
import org.springframework.data.relational.core.sql.Visitor;
import org.springframework.data.relational.core.sql.Where;
import org.springframework.lang.Nullable;

/**
 * The type Select interceptor.
 *
 * @author mengzz
 */
public class SelectVisitor implements Visitor {
    private SelectList selectList;
    @Nullable
    private Where where;

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
        }
    }

}
