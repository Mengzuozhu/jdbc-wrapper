package com.github.mengzz.jdbc.wrapper.visitor;

import lombok.Getter;
import org.springframework.data.relational.core.sql.*;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Sql visitor.
 *
 * @author mengzz
 */
@Getter
public class SqlVisitor implements Visitor {
    @Nullable
    private Where where;
    private List<Join> joins = new ArrayList<>();
    private SelectList selectList;
    private From from;
    private Table table;
    private List<Assignment> assignments = new ArrayList<>();

    public SqlVisitor() {
    }

    public static SqlVisitor of() {
        return new SqlVisitor();
    }

    public static SqlVisitor visit(Visitable visitable) {
        SqlVisitor interceptor = of();
        visitable.visit(interceptor);
        return interceptor;
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
        } else if (segment instanceof Table) {
            table = (Table) segment;
        } else if (segment instanceof Assignment) {
            assignments.add((Assignment) segment);
        }
    }
}
