package com.cxy.filterFactory;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class AuthorizationGlobalFilter implements GlobalFilter {
    //权限等级 至少是管理员级以上
    final int POWER_LEVEL = 1;
    //权限类型 必须是admin
    final String POWER_TYPE = "admin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
        //判断是不是公开接口
        if (!uri.getPath().contains("/public")) {

            //不是公开接口就验证jwt
            String token = exchange.getRequest().getHeaders().getFirst("token");
            //判空验证
            if (StringUtils.isEmpty(token)) {
                return this.loginOut(exchange);
            }
            //鉴定权限
            JWT jwt = null;
            try {
                jwt = JWTUtil.parseToken(token);
            } catch (Exception e) {
                e.printStackTrace();
                return loginOut(exchange);
            }
            if (jwt == null) {
                return loginOut(exchange);
            }

            String type = (String) jwt.getPayload("type");
            Integer power = (Integer) jwt.getPayload("power");

            //超级管理员就直接过
            if ("root".equals(type)) {

                return chain.filter(exchange);
            }
            //如果是普通管理员就放过
            else if (POWER_TYPE.equals(type) && power >= POWER_LEVEL) {
                return chain.filter(exchange);
            }


            //不是普通管理员并且权限等级等于1的拦截
            return loginOut(exchange);

        }

        return chain.filter(exchange);

    }


    public Mono<Void> loginOut(ServerWebExchange exchange) {

        Result fail = Result.fail(ResultEnum.LOGIN_EXPIRES);
        byte[] bytes = JSONObject.toJSONString(fail).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(HttpStatus.OK);

        //写入到回复流中
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(Flux.just(buffer));


    }


}
