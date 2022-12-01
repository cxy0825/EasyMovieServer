package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 电影院信息表
 *
 * @TableName cinema
 */
@TableName(value = "cinema")
@Data
public class Cinema implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 电影院名字
     */
    private String cinemaName;

    /**
     * 经度
     */
    private BigDecimal x;

    /**
     * 纬度
     */
    private BigDecimal y;

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