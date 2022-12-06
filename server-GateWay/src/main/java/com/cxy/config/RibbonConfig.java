package com.cxy.config;

import com.netflix.loadbalancer.ServerListUpdater;
import org.springframework.context.annotation.Bean;

//@Configuration
//@ConditionalOnRibbonNacos
public class RibbonConfig {
    @Bean
    public ServerListUpdater ribbonServerListUpdater(NacosServerListListener listener) {
        return new NotificationServerListUpdater(listener);
    }
}