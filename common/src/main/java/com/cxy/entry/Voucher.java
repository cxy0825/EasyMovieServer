package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 全局优惠券所有店铺都能使用
 *
 * @TableName voucher
 */
@TableName(value = "voucher")
@Data
public class Voucher implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 电影院的ID
     */
    private Long cinemaId;
    /**
     * 电影院的名字
     */
    private String cinemaName;
    /**
     * 优惠券标题
     */
    private String title;

    /**
     * 使用规则
     */
    private String rule;

    /**
     * 支付多少金额能使用
     */
    private Long payValue;

    /**
     * 抵扣多少金额
     */
    private Long actualValue;
    /*
     * 优惠券价格
     * */
    private Long price;
    /**
     * 0表示下架 1表示上架
     */
    private Integer state;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 优惠券开始使用时间
     */
    private LocalDateTime startTime;

    /**
     * 优惠券截止使用时间
     */
    private LocalDateTime endTime;

    /**
     *
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime createTime;

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