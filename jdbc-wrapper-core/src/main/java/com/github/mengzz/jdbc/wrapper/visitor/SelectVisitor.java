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
    private List<Expression> selectList;
    private Condition condition;
    private List<Join> joins = new ArrayList<>();

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

    @Nullable
    public Where getWhere() {
        return where;
    }

    @Override
    public void enter(Visitable segment) {
        if (segment instanceof Where) {
            where = (Where) segment;
            condition = ConditionVisitor.visit(segment).getCondition();
        } else if (segment instanceof SelectList) {
            selectList = ExpressionVisitor.visit(segment).getExpressions();
        }

        if (segment instanceof Join) {
            joins.add((Join) segment);
        }
    }


    public Select copyWithoutJoin() {
        SelectBuilder.SelectAndFrom builder = Select.builder()
                .select(selectList);
        if (select.isDistinct()) {
            builder = builder.distinct();
        }
        Select result = builder.from(this.select.getFrom().getTables())
                .limitOffset(this.select.getLimit().orElse(-1),
                        this.select.getOffset().orElse(-1))
                .where(condition)
                .orderBy(this.select.getOrderBy())
                .lock(this.select.getLockMode())
                .build();
        return result;

    }
}
