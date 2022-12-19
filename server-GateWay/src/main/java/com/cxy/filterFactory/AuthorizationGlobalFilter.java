package com.cxy.filterFactory;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.cxy.clients.mongo.RedisClient;
import com.cxy.entry.Token;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class AuthorizationGlobalFilter implements GlobalFilter {
    @Resource
    RedisClient redisClient;
    //权限等级 至少是管理员级以上
    final int POWER_LEVEL = 1;
    //权限类型 必须是admin
    final String POWER_TYPE = "admin";
    final AntPathMatcher antPathMatcher = new AntPathMatcher();
    //不需要进行校验的路径接口
    final String[] urls = {
            "/**/public/**",
            "/**/login/**",
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
//        System.out.println(uri);
        boolean match = false;
        //判断是否是公开请求
        for (String url : urls) {
            match = antPathMatcher.match(url, uri.getPath());
            if (match) {
                break;
            }
        }
        //如果不是公开请求就进行权限判断
        if (!match) {
            //先去redis中拿用户详情
            //不是公开接口就验证jwt
            String token = exchange.getRequest().getHeaders().getFirst("token");
            String account = null;
            String sign = null;
            try {
                //账号
                account = (String) JWTUtil.parseToken(token).getPayload("account");
                //签名
                sign = token.substring(token.lastIndexOf(".") + 1);
            } catch (Exception e) {
                loginOut(exchange);
                System.out.println("token解析异常");
            }

            if (token == null) {
                return loginOut(exchange);
            }
            Token user = redisClient.getUserInfo(account, sign);
            if (user == null) {
                return loginOut(exchange);
            }
            //添加到请求头
            exchange.getRequest().mutate().header("userInfo", JSONUtil.toJsonStr(user)).build();


        }
        return chain.filter(exchange);

    }


    public Mono<Void> loginOut(ServerWebExchange exchange) {

        Result fail = Result.fail(ResultEnum.LOGIN_EXPIRES).message("拦截成功,登陆过期");
        byte[] bytes = JSONObject.toJSONString(fail).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(HttpStatus.OK);

        //写入到回复流中
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(Flux.just(buffer));


    }


}
