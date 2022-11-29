package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName film_info
 */
@TableName(value = "film_info")
@Data
public class FilmInfo implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 电影ID
     */
    private Long filmId;

    /**
     * 宣传片地址
     */
    private String videoUrl;

    /**
     * 海报地址
     */
    private String posterUrl;

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