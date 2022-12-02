package com.cxy.service;

import com.cxy.entry.Administrator;
import com.cxy.entry.User;
import com.cxy.result.Result;

import java.util.Map;

public interface AuthorizationService {
    //针对普通用户
    Map<String, Object> createToken(User user);  //针对管理员

    String createToken(Administrator administrator);

    //验证token是否合法
    Boolean verify(String token);

    //检查token并判断是否需要刷新token
    Result CheckToken(String token, String refreshToken);

}
