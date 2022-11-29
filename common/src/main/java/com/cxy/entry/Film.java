package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 电影影片信息
 *
 * @TableName film
 */
@TableName(value = "film")
@Data
public class Film implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 影片名字
     */
    private String filmName;

    /**
     * 影片简介
     */
    private String about;

    /**
     * 电影上映时间
     */
    private LocalDateTime releaseTime;

    /**
     * 电影时长
     */
    private Double duration;

    /**
     * 电影评分
     */
    private Double score;

    /**
     *
     */
    private LocalDateTime credateTime;

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