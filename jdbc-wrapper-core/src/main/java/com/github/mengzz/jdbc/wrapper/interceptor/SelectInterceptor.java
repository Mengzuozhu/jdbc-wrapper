package com.github.mengzz.jdbc.wrapper.interceptor;

import org.springframework.data.relational.core.sql.From;
import org.springframework.data.relational.core.sql.SelectList;
import org.springframework.data.relational.core.sql.Visitable;
import org.springframework.data.relational.core.sql.Where;
import org.springframework.lang.Nullable;

/**
 * The type Select interceptor.
 *
 * @author mengzz
 */
public class SelectInterceptor implements Interceptor {
    private SelectList selectList;
    private From from;
    @Nullable
    private Where where;

    public static SelectInterceptor of() {
        return new SelectInterceptor();
    }

    public static SelectInterceptor visit(Visitable visitable) {
        SelectInterceptor interceptor = of();
        visitable.visit(interceptor);
        return interceptor;
    }

    public SelectList getSelectList() {
        return selectList;
    }

    public From getFrom() {
        return from;
    }

    @Nullable
    public Where getWhere() {
        return where;
    }

    @Override
    public void intercept(Visitable segment) {
        if (segment == null) {
            return;
        }

        if (segment instanceof Where) {
            where = (Where) segment;
        } else if (segment instanceof SelectList) {
            selectList = (SelectList) segment;
        } else if (segment instanceof From) {
            from = (From) segment;
        }
    }

}
