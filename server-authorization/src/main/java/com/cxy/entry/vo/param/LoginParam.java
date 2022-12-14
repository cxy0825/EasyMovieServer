package com.cxy.entry.vo.param;

import lombok.Data;

@Data
public class LoginParam {
    //账号 就是手机号
    public String account;
    //密码
    public String password;
    //登录类型 支付宝登录 EM登录
    public String type;
}
