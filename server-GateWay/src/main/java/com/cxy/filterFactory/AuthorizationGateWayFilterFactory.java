package com.cxy.filterFactory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import java.util.Arrays;
import java.util.List;

public class AuthorizationGateWayFilterFactory extends AbstractGatewayFilterFactory<AuthorizationGateWayFilterFactory.Config> {


    public AuthorizationGateWayFilterFactory() {
        super(AuthorizationGateWayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
//            RequestPath path = exchange.getRequest().getPath();


            return chain.filter(exchange);
        };
    }


    public static class Config {

    }
}
