package com.github.mengzz.jdbc.wrapper.visitor;

import org.springframework.data.relational.core.sql.Visitable;
import org.springframework.data.relational.core.sql.Visitor;

/**
 * The type Abstract visitor.
 *
 * @param <T> the type parameter
 * @author mengzz
 */
public abstract class AbstractVisitor<T> implements Visitor {
    @Override
    public void enter(Visitable segment) {
        Class<T> generic = getDomainType();
        if (generic.isInstance(segment)) {
            T cast = generic.cast(segment);
            intercept(cast);
        }
    }

    /**
     * Gets domain type.
     *
     * @return the generic class
     */
    public abstract Class<T> getDomainType();

    /**
     * Intercept.
     *
     * @param segment the segment
     */
    public abstract void intercept(T segment);

}
