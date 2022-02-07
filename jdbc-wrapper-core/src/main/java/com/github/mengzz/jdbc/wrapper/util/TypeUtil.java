package com.github.mengzz.jdbc.wrapper.util;

import java.util.function.Consumer;

/**
 * The type Type util.
 *
 * @author mengzz
 */
class TypeUtil {
    /**
     * Do if class.
     *
     * @param <T>      the type parameter
     * @param obj      the obj
     * @param type     the type
     * @param consumer the consumer
     */
    public static <T> void doIfClass(Object obj, Class<T> type, Consumer<T> consumer) {
        if (type.isInstance(obj)) {
            consumer.accept(type.cast(obj));
        }
    }

    /**
     * Try convert.
     *
     * @param <T>  the type parameter
     * @param obj  the obj
     * @param type the type
     * @return the t
     */
    public static <T> T tryConvert(Object obj, Class<T> type) {
        if (type.isInstance(obj)) {
            return type.cast(obj);
        }
        return null;
    }
}
