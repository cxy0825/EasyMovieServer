package com.cxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.cxy.entry.Token;
import com.cxy.result.redisKey;
import com.cxy.service.UserService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    //解决gateway在使用feign调用找不到bean的问题
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public Token getUserInfo(String account, String key) {
        //先判断这个key有没有存在
        String userKey = redisKey.USER_TOKEN_KEY.getKey() + account + ":" + key;
        Boolean hasKey = redisTemplate.hasKey(userKey);
        //不存在这个Key说明redis中登录信息过期
        if (!hasKey) {
            return null;
        }

        //如果存在就查询信息
        Map entries = redisTemplate.opsForHash().entries(userKey);

//        System.out.println(entries.toString());
        //判断这个key还有多久过期
        //如果小于180分钟就刷新
        Long expire = redisTemplate.getExpire(userKey, TimeUnit.MINUTES);
        if (expire < 180) {
            redisTemplate.expire(userKey, 5, TimeUnit.DAYS);
        }
        Token token = BeanUtil.mapToBean(entries, Token.class, false);
        return token;
    }

    @Override
    public boolean setUserInfo(String account, String key, Map<String, Object> userMap) {
        //先获取这个账号有没有登录过
        Set keys = redisTemplate.keys(redisKey.USER_TOKEN_KEY.getKey().concat(account + ":*"));
        //如果有存在,就删除掉再加入
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
        try {
            String userKey = redisKey.USER_TOKEN_KEY.getKey() + account + ":" + key;
            redisTemplate.opsForHash().putAll(userKey, userMap);
            Boolean expire = redisTemplate.expire(userKey, 5, TimeUnit.DAYS);
            return expire;
        } catch (Exception e) {
            return false;
        }

    }
}
