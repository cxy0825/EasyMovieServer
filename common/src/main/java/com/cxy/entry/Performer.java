package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 演员信息表
 *
 * @TableName performer
 */
@TableName(value = "performer")
@Data
public class Performer implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 演员名字
     */
    private String performerName;

    /**
     * 演员照片地址
     */
    private String performerPicUrl;

    /**
     * 演员出生日期
     */
    private LocalDateTime birthday;

    /**
     * 演员简介
     */
    private String performerAbout;

    /**
     *
     */
    private LocalDateTime createTime;

    /**
     *
     */
    private LocalDateTime updateTime;

    /**
     *
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}