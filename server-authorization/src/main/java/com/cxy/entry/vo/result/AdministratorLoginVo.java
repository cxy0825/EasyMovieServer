package com.cxy.entry.vo.result;

import lombok.Data;

@Data
public class AdministratorLoginVo {
    private Long id;

    /**
     * 用户名称最长16个字
     */
    private String name;
    //账号
    private String account;
    //电影院ID
    private Long cinemaId;
    
    private String token;
    //权限等级
    private Integer power;
}
