package com.cxy.config;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ServerListUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class NotificationServerListUpdater implements ServerListUpdater {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServerListUpdater.class);

    private final ServerListListener listener;

    public NotificationServerListUpdater(ServerListListener listener) {
        this.listener = listener;
    }

    /**
     * 开始运行
     *
     * @param updateAction
     */
    @Override
    public void start(UpdateAction updateAction) {
        // 创建监听
        String clientName = getClientName(updateAction);
        listener.listen(clientName, () -> {
            logger.info("{} 服务变化, 主动刷新服务列表缓存", clientName);
            // 回调直接更新
            updateAction.doUpdate();
        });
    }

    @Override
    public void stop() {

    }

    @Override
    public String getLastUpdate() {
        return null;
    }

    @Override
    public long getDurationSinceLastUpdateMs() {
        return 0;
    }

    @Override
    public int getNumberMissedCycles() {
        return 0;
    }

    @Override
    public int getCoreThreads() {
        return 0;
    }

    /**
     * 通过updateAction获取服务名，这种方法比较粗暴
     *
     * @param updateAction
     * @return
     */
    private String getClientName(UpdateAction updateAction) {
        try {
            Class<?> bc = updateAction.getClass();
            Field field = bc.getDeclaredField("this$0");
            field.setAccessible(true);
            BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) field.get(updateAction);
            return baseLoadBalancer.getClientConfig().getClientName();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}