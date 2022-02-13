package com.github.mengzz.jdbc.wrapper.wrapper;

import com.github.mengzz.jdbc.wrapper.interceptor.SqlInterceptor;
import com.github.mengzz.jdbc.wrapper.proxy.SelectProxy;
import com.github.mengzz.jdbc.wrapper.proxy.SqlProxy;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.RenderContextFactory;
import org.springframework.data.relational.core.sql.*;
import org.springframework.data.relational.core.sql.render.SqlRenderer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengzz
 **/
public class RendererWrapper {
    private SqlRenderer sqlRenderer;
    private List<SqlInterceptor> sqlInterceptors = new ArrayList<>();

    public RendererWrapper(Dialect dialect) {
        sqlRenderer = SqlRenderer.create(new RenderContextFactory(dialect).createRenderContext());
    }

    public static RendererWrapper create(Dialect dialect) {
        return new RendererWrapper(dialect);
    }

    public static String render(Dialect dialect, Segment segment) {
        return new RendererWrapper(dialect).render(segment);
    }

    public void setSqlInterceptors(List<SqlInterceptor> sqlInterceptors) {
        this.sqlInterceptors = sqlInterceptors;
    }

    /**
     * Render.
     *
     * @param segment the segment
     * @return the string
     */
    public String render(Segment segment) {

        if (segment instanceof Select) {
            SelectProxy selectProxy = new SelectProxy((Select) segment);
            intercept(selectProxy);
            return sqlRenderer.render(selectProxy);
        }
        if (segment instanceof Delete) {
            return sqlRenderer.render((Delete) segment);
        }
        if (segment instanceof Update) {
            return sqlRenderer.render((Update) segment);
        }
        if (segment instanceof Insert) {
            return sqlRenderer.render((Insert) segment);
        }
        throw new UnsupportedOperationException("Unsupported SQL");
    }

    private SqlProxy intercept(SqlProxy segment) {
        if (!CollectionUtils.isEmpty(sqlInterceptors)) {
            // custom interceptor
            for (SqlInterceptor visitor : sqlInterceptors) {
                segment = visitor.intercept(segment);
            }
        }
        return segment;
    }

}
