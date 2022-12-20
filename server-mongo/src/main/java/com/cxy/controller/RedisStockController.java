package com.cxy.controller;

import cn.hutool.core.util.BooleanUtil;
import com.cxy.service.RedisStockService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("redis/stock")
public class RedisStockController {
    @Resource
    RedisStockService redisStockService;
    @Resource
    RedisTemplate redisTemplate;

    /**
     * 得到库存数量
     *
     * @param key 关键
     * @return {@link Long}
     */
    @GetMapping("getStock/{key}")
    public Long getStock(@PathVariable("key") String key) {
        return redisStockService.getStock(key);
    }

    /**
     * 设置库存
     *
     * @param key      关键
     * @param stockNum 库存数量
     * @return boolean
     */
    @PostMapping("setStock/{key}/{stockNum}")
    public boolean setStock(@PathVariable("key") String key, @PathVariable("stockNum") Integer stockNum) {
        return redisStockService.setStock(key, stockNum);
    }

    /**
     * 删除库存
     *
     * @param key redis键
     * @return boolean
     */
    @GetMapping("delStock/{key}")
    public boolean delStock(@PathVariable String key) {
        Boolean delete = redisTemplate.delete(key);
        return BooleanUtil.isTrue(delete);
    }

    /**
     * 获得已经购买过的列表
     *
     * @param key 关键
     * @return {@link List}<{@link Long}>
     */
    @GetMapping("alreadyBought/{key}")
    public List<Long> getAlreadyBought(@PathVariable("key") String key) {
        return redisStockService.getAlreadyBought(key);
    }

    /**
     * 购买商品 限制购买的
     *
     * @param stockKey  redis中商品的库存key
     * @param boughtKey redis中购买过该商品的key
     * @param userId    购买者的ID
     * @return {@link List}<{@link Long}>
     */
    @PostMapping("limitBought/{stockKey}/{boughtKey}/{userId}/{number}")
    public boolean limitBought(
            @PathVariable("stockKey") String stockKey,
            @PathVariable("boughtKey") String boughtKey,
            @PathVariable("userId") Long userId,
            @PathVariable("number") String number
    ) {
        return redisStockService.limitBought(stockKey, boughtKey, userId, number);
    }

    /**
     * 购买商品
     *
     * @param stockKey redis中商品的库存key
     * @return {@link List}<{@link Long}>
     */
    @PostMapping("bought/{stockKey}/{userId}/{number}")
    public boolean bought(
            @PathVariable("stockKey") String stockKey,
            @PathVariable("userId") Long userId,
            @PathVariable("number") String number
    ) {
        return redisStockService.bought(stockKey, userId, number);
    }
}
