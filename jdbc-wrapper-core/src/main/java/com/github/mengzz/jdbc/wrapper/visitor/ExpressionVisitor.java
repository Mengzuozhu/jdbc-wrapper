package com.github.mengzz.jdbc.wrapper.visitor;

import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Visitable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengzz
 **/
public class ExpressionVisitor extends AbstractVisitor<Expression> {
    private List<Expression> expressions = new ArrayList<>();

    public static ExpressionVisitor of() {
        return new ExpressionVisitor();
    }

    public static ExpressionVisitor visit(Visitable visitable) {
        ExpressionVisitor interceptor = new ExpressionVisitor();
        visitable.visit(interceptor);
        return interceptor;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public void intercept(Expression expression) {
        expressions.add(expression);
    }
}
