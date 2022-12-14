package com.cxy.entry;

import lombok.Data;

import java.io.Serializable;

@Data
public class Token implements Serializable {
    //用户ID
    private Long id;
    //用户名字
    private String name;
    //用户账号
    private String account;
    //电影院ID 管理员用得到 用户为null
    private Long cinemaId;
    //用户类型 普通用户=user VIP用户=vip 普通电影院管理员=admin 最高管理员=root
    private String type;
    //权限等级
    private String power;
    //用户头像 管理员用不到
    private String avatarUrl;
}
