package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName moviehouse
 */
@TableName(value = "moviehouse")
@Data
public class Moviehouse implements Serializable {
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
     * 放映厅名字
     */
    private String movieHouseName;

    /**
     * 放映厅状态 0表示停用 1表示可以使用 2表示维修中
     */
    private Integer state;

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