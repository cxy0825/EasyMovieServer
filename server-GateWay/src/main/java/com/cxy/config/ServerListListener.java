package com.cxy.config;

/**
 * @Author pq
 * @Date 2022/4/26 17:19
 * @Description 抽象监听器
 */
public interface ServerListListener {
    /**
     * 监听
     *
     * @param serviceId    服务名
     * @param eventHandler 回调
     */
    void listen(String serviceId, ServerEventHandler eventHandler);

    @FunctionalInterface
    interface ServerEventHandler {
        void update();
    }
}