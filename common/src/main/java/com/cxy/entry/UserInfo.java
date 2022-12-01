package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表的拓展信息字段
 *
 * @TableName user_info
 */
@TableName(value = "user_info")
@Data
public class UserInfo implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private Integer userId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String sex;

    /**
     *
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime createTime;
    ;

    /**
     *
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime updateTime;

    /**
     *
     */

    @TableLogic
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}