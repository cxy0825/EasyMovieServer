package com.cxy.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum redisKey {
    //用户登录TOKEN
    USER_TOKEN_KEY("user:token:"),
    //优惠券的库存数量
    VOUCHER_STOCK("voucher:stock:"),
    //已经购买过此优惠券的用户
    VOUCHER_ALREADY_BOUGHT("voucher:already:bought:");
    private String key;

}
