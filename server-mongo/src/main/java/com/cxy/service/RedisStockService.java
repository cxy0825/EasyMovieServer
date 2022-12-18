package com.cxy.service;

import java.util.List;

public interface RedisStockService {
    Long getStock(String key);

    boolean setStock(String key, Integer stockNum);

    List<Long> getAlreadyBought(String key);

    boolean limitBought(String stockKey, String boughtKey, Long userId);

    boolean bought(String stockKey, Long userId);

}
