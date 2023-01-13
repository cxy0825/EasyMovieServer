package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 优惠券订单信息表
 *
 * @TableName voucher_order
 */
@TableName(value = "hold_voucher")
@Data
public class holdVoucher implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     * 优惠券ID
     */
    private Long voucherId;
    /*
     *
     * 是否使用
     * */
    /**
     * 抢到优惠券的用户ID
     */
    private Long userId;

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
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}