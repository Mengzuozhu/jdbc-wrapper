package com.github.mengzz.jdbc.wrapper.wrapper;

import com.github.mengzz.jdbc.wrapper.interceptor.SqlInterceptor;
import com.github.mengzz.jdbc.wrapper.proxy.DeleteProxy;
import com.github.mengzz.jdbc.wrapper.proxy.SelectProxy;
import com.github.mengzz.jdbc.wrapper.proxy.SqlProxy;
import com.github.mengzz.jdbc.wrapper.proxy.UpdateProxy;
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
            SelectProxy proxy = SelectProxy.of((Select) segment);
            intercept(proxy);
            return sqlRenderer.render(proxy);
        }
        if (segment instanceof Delete) {
            DeleteProxy proxy = DeleteProxy.of((Delete) segment);
            intercept(proxy);
            return sqlRenderer.render(proxy);
        }
        if (segment instanceof Update) {
            UpdateProxy proxy = UpdateProxy.of((Update) segment);
            intercept(proxy);
            return sqlRenderer.render(proxy);
        }
        if (segment instanceof Insert) {
            return sqlRenderer.render((Insert) segment);
        }

        throw new UnsupportedOperationException("Unsupported SQL");
    }

    private void intercept(SqlProxy sqlProxy) {
        if (!CollectionUtils.isEmpty(sqlInterceptors)) {
            // custom interceptor
            for (SqlInterceptor interceptor : sqlInterceptors) {
                interceptor.intercept(sqlProxy);
            }
        }
    }

}
