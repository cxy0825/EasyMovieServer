package com.cxy.service;

import com.cxy.entry.Token;

import java.util.Map;

public interface UserService {
    Token getUserInfo(String account, String key);

    boolean setUserInfo(String account, String key, Map<String, Object> userMap);
}
