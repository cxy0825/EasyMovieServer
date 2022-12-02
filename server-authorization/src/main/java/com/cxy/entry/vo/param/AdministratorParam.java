package com.cxy.entry.vo.param;

import lombok.Data;

@Data
public class AdministratorParam {
    //账号
    public String account;
    //密码
    public String password;
    //登录类型 支付宝登录 EM登录
    public String type;
}
