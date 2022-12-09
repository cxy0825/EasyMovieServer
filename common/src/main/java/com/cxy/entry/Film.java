package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
     * 电影院ID
     */
    private Long cinemaId;
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
    //电影宣传视频地址
    @TableField(exist = false)
    private Set<String> videoUrls = new HashSet<>();
    //电影宣传海报地址
    @TableField(exist = false)
    private Set<String> posterUrls = new HashSet<>();

    //电影标签
    @TableField(exist = false)
    private Set<String> tags = new HashSet<>();
    /**
     *
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime credateTime;

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