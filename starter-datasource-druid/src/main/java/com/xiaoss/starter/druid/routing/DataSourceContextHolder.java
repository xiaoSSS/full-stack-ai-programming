package com.xiaoss.starter.druid.routing;

import java.util.ArrayDeque;
import java.util.Deque;

public final class DataSourceContextHolder {

    private static final ThreadLocal<Deque<DataSourceType>> CONTEXT = ThreadLocal.withInitial(ArrayDeque::new);

    private DataSourceContextHolder() {
    }

    public static void useRead() {
        CONTEXT.get().push(DataSourceType.READ);
    }

    public static void useWrite() {
        CONTEXT.get().push(DataSourceType.WRITE);
    }

    public static DataSourceType current() {
        return CONTEXT.get().peek();
    }

    public static void clear() {
        Deque<DataSourceType> deque = CONTEXT.get();
        if (!deque.isEmpty()) {
            deque.pop();
        }
        if (deque.isEmpty()) {
            CONTEXT.remove();
        }
    }
}
