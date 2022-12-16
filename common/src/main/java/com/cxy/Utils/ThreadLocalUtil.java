package com.cxy.Utils;

import org.springframework.stereotype.Component;

@Component
public class ThreadLocalUtil {
    private final static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    /**
     * 设置数据到当前线程
     */
    public static void set(Object o) {
        threadLocal.set(o);
    }

    /**
     * 获取线程中的数据
     *
     * @return
     */
    public static Object get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
