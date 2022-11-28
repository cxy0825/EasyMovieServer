package com.cxy.entry.vo.result;

import lombok.Data;

import java.util.Map;

@Data
public class LoginVo {
    private Long id;

    /**
     * 用户名称最长16个字
     */
    private String nickName;
    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String phone;
    private Map<String, Object> tokenMap;
}
