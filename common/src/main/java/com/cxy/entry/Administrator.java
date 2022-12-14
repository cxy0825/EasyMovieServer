package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName administrator
 */
@TableName(value = "administrator")
@Data
public class Administrator implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 电影院ID
     */
    private Long cinemaId;

    /**
     * 管理员名字
     */
    private String name;

    /**
     * 管理员登录账号
     */
    private String account;

    /**
     * 管理员登录密码
     */
    private String password;

    /**
     * 权限等级 1000表示最高管理员 1表示电影院管理员
     */
    private Integer power;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private transient LocalDateTime createTime;
    ;

    /**
     *
     */

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private transient LocalDateTime updateTime;

    /**
     *
     */

    @TableLogic
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private transient Integer isDelete;

    @TableField(exist = false)
    @Transient
    private static final long serialVersionUID = 1L;
}