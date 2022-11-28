package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 第三方信息用户表
 *
 * @TableName oauths
 */
@TableName(value = "oauths")
@Data
public class Oauths implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 对应user表中的用户ID
     */
    private Long userId;

    /**
     * 第三方登陆类型 weibo、qq、wechat 等
     */
    private String oauthType;

    /**
     * 第三方 uid 、openid 等
     */
    private String oauthId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除 0不删除 1删除
     */

    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}