package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 演员和影片之间的关系表
 *
 * @TableName performer_index
 */
@TableName(value = "performer_index")
@Data
public class PerformerIndex implements Serializable {
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
     * 演员ID
     */
    private Long performerId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}