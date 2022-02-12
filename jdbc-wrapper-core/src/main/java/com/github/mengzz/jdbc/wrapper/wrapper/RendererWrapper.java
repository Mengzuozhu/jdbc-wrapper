package com.github.mengzz.jdbc.wrapper.wrapper;

import com.github.mengzz.jdbc.wrapper.interceptor.Interceptor;
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
    private List<Interceptor> interceptors = new ArrayList<>();

    public RendererWrapper(Dialect dialect) {
        sqlRenderer = SqlRenderer.create(new RenderContextFactory(dialect).createRenderContext());
    }

    public static RendererWrapper create(Dialect dialect) {
        return new RendererWrapper(dialect);
    }

    public static String render(Dialect dialect, Segment segment) {
        return new RendererWrapper(dialect).render(segment);
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    /**
     * Render.
     *
     * @param segment the segment
     * @return the string
     */
    public String render(Segment segment) {
        if (!CollectionUtils.isEmpty(interceptors)) {
            // custom interceptor
            for (Interceptor visitor : interceptors) {
                segment = visitor.intercept(segment);
            }
        }

        if (segment instanceof Select) {
            return sqlRenderer.render((Select) segment);
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

}
