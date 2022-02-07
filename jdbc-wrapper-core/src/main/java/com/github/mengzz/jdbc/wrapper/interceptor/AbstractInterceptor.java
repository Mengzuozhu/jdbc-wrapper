package com.github.mengzz.jdbc.wrapper.interceptor;

import org.springframework.data.relational.core.sql.Visitable;

/**
 * The type Abstract interceptor.
 *
 * @param <T> the type parameter
 * @author mengzz
 */
public abstract class AbstractInterceptor<T> implements Interceptor {
    @Override
    public void intercept(Visitable segment) {
        if (segment == null) {
            return;
        }

        Class<T> generic = getGenericClass();
        if (generic.isInstance(segment)) {
            T cast = generic.cast(segment);
            intercept(cast);
        }
    }

    /**
     * Gets generic class.
     *
     * @return the generic class
     */
    public abstract Class<T> getGenericClass();

    /**
     * Intercept.
     *
     * @param segment the segment
     */
    public abstract void intercept(T segment);

}
