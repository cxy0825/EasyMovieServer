package com.cxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.jwt.JWT;
import com.cxy.clients.mongo.RedisClient;
import com.cxy.entry.Token;
import com.cxy.service.AuthorizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorizationImpl implements AuthorizationService {
    @Resource
    RedisClient redisClient;
    final byte[] key = "zxc5646@#@$".getBytes(StandardCharsets.UTF_8);


    @Override
    public String createToken(Token token) {

        Map<String, Object> map = BeanUtil.beanToMap(token,
                new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true)//忽略空值
        );

        String stringToken = JWT.create()
                .setPayload("ID", token.getId())
                .setPayload("cinemaID", token.getCinemaId())
                .setPayload("power", token.getPower())
                .setPayload("type", token.getName())
                .setPayload("account", token.getAccount())
                .setKey(key)
                .sign();
        String sign = stringToken.substring(stringToken.lastIndexOf(".") + 1);
//        //存到redis中
        redisClient.setUserInfo(token.getAccount(), sign, map);
        return stringToken;
    }


}
