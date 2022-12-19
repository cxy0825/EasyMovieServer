package com.cxy.service;

import java.util.List;

public interface RedisStockService {
    Long getStock(String key);

    boolean setStock(String key, Integer stockNum);

    List<Long> getAlreadyBought(String key);

    boolean limitBought(String stockKey, String boughtKey, Long userId, String number);

    boolean bought(String stockKey, Long userId, String number);

}
