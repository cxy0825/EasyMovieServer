package com.cxy.service;

import com.cxy.entry.User;
import com.cxy.result.Result;

import java.util.Map;

public interface AuthorizationService {
    Map<String, Object> createToken(User user);

    //验证token是否合法
    Boolean verify(String token);

    //检查token并判断是否需要刷新token
    Result CheckToken(String token, String refreshToken);
}
