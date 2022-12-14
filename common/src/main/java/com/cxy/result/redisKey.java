package com.cxy.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum redisKey {
    //用户登录TOKEN
    USER_TOKEN_KEY("user:token:");
    private String key;

}
