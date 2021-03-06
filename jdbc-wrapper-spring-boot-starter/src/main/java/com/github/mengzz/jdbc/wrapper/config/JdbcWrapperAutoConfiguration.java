package com.github.mengzz.jdbc.wrapper.config;

import com.github.mengzz.jdbc.wrapper.core.JdbcWrapper;
import com.github.mengzz.jdbc.wrapper.core.JdbcWrapperImpl;
import com.github.mengzz.jdbc.wrapper.interceptor.SqlInterceptor;
import com.github.mengzz.jdbc.wrapper.wrapper.RendererWrapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;

/**
 * @author mengzz
 */
@Configuration
public class JdbcWrapperAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(JdbcWrapper.class)
    public JdbcWrapper jdbcWrapper(RelationalMappingContext mappingContext,
                                   NamedParameterJdbcOperations operations,
                                   JdbcConverter converter,
                                   Dialect dialect,
                                   ObjectProvider<List<SqlInterceptor>> interceptors) {
        JdbcWrapperImpl jdbcDecorator = new JdbcWrapperImpl(operations, mappingContext, converter, dialect);
        jdbcDecorator.setInterceptors(interceptors.getIfAvailable());
        return jdbcDecorator;
    }

    @Bean
    @ConditionalOnMissingBean(RendererWrapper.class)
    public RendererWrapper rendererWrapper(Dialect dialect) {
        return RendererWrapper.create(dialect);
    }

}
