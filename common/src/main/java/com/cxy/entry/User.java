package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户基本信息表
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * 用户ID,递增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称最长16个字
     */
    private String nickName;

    /**
     * 用户密码最长22个字
     */
    private String password;

    /**
     * 用户权限等级 1表示普通用户 2表示Vip用户 100表示管理员
     */
    private Integer power;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户状态 1表示正常用户,0表示封号
     */
    private String state;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}