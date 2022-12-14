package com.cxy.service;

import com.cxy.entry.Token;

public interface AuthorizationService {
    //针对普通用户


    String createToken(Token token);


}
