package com.cxy.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MovieSetDto implements Serializable {
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
     * 放映厅名字
     */
    private String movieHouseName;
    /**
     * 影片名字
     */
    private String filmName;
    /**
     * 电影时长
     */
    private Double duration;
    /**
     * 电影院名字
     */
    private String cinemaName;
}
