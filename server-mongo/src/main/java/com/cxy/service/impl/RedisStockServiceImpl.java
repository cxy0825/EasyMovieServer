package com.cxy.service.impl;

import com.cxy.service.RedisStockService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RedisStockServiceImpl implements RedisStockService {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    RabbitTemplate rabbitTemplate;

    @Override
    public Long getStock(String key) {
        return Long.valueOf(String.valueOf(redisTemplate.opsForValue().get(key)));

    }

    @Override
    public boolean setStock(String key, Integer stockNum) {
        redisTemplate.opsForValue().set(key, stockNum);
        System.out.println(redisTemplate.opsForValue().get("voucher:stock:1"));
        return true;

    }

    @Override
    public List<Long> getAlreadyBought(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    @Override
    public boolean limitBought(String stockKey, String boughtKey, Long userId) {
        //放在一个线程里面使用watch保证并发的时候不会出问题
        boolean result = (Boolean) redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.watch(stockKey);
                //判断库存是否大于0
                Integer stockNum = (Integer) redisTemplate.opsForValue().get(stockKey);
                //判断是否购买过
                Boolean member = redisTemplate.opsForSet().isMember(boughtKey, userId);
                //如果购买过
                if (member != null && member) {
                    return false;
                }

//
                if (stockNum != null && stockNum > 0) {
                    //开启事务
                    redisOperations.multi();
                    //把用户ID加入到队列
                    redisTemplate.opsForSet().add(boughtKey, userId);
                    //扣减库存
                    redisTemplate.opsForValue().decrement(stockKey);
                    //执行事务
                    redisOperations.exec();


                    return true;

                }
                return false;

            }

        });
        return result;
    }

    @Override
    public boolean bought(String stockKey, Long userId) {

        //放在一个线程里面使用watch保证并发的时候不会出问题
        boolean result = (Boolean) redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                //开启事务
                redisOperations.multi();
                //判断库存是否大于0
                Integer stockNum = (Integer) redisTemplate.opsForValue().get(stockKey);
                if (stockNum != null && stockNum > 0) {
                    //扣减库存
                    Long decrement = redisTemplate.opsForValue().decrement(stockKey);
                    //执行事务
                    redisOperations.exec();
                    if (decrement != null) {

                        return true;
                    }
                }
                return false;

            }

        });
        return result;
    }


}
