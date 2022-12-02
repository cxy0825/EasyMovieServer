package com.cxy.entry.vo.result;

import lombok.Data;

@Data
public class AdministratorLoginVo {
    private Long id;

    /**
     * 用户名称最长16个字
     */
    private String name;

    private String account;

    private Long cinemaId;
    private String token;

    private Integer power;
}
