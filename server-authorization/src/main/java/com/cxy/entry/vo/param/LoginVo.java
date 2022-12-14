package com.cxy.entry.vo.param;

import lombok.Data;

@Data
public class LoginVo {
    String account;
    String password;
    //登录方式
    String type;


}
