package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 排片信息
 *
 * @TableName movie_set
 */
@TableName(value = "movie_set")
@Data
public class MovieSet implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 电影放映厅ID
     */
    private Long movieHouseId;

    /**
     * 影片ID
     */
    private Long filmId;

    /**
     * 电影院ID
     */
    private Long cinemaId;

    /**
     * 电影开始时间
     */
    private LocalDateTime movieStartTime;

    /**
     * 电影结束时间
     */
    private LocalDateTime movieEndTime;

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