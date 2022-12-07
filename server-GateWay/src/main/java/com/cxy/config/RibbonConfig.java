package com.cxy.config;

import com.alibaba.cloud.nacos.ribbon.ConditionalOnRibbonNacos;
import com.netflix.loadbalancer.ServerListUpdater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnRibbonNacos
public class RibbonConfig {
    @Bean
    public ServerListUpdater ribbonServerListUpdater(NacosServerListListener listener) {
        return new NotificationServerListUpdater(listener);
    }
}