package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 排片信息
 *
 * @TableName movie_set
 */
@TableName(value = "movie_set")
@Document("em_movieSet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSet implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @Id
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime movieStartTime;

    /**
     * 电影结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime movieEndTime;

    /**
     * 票价
     */
    private BigDecimal price;
    /**
     * 该排片已经购买的票
     */
    @TableField(exist = false)
    private Integer[][] seatInfo = new Integer[][]{};
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