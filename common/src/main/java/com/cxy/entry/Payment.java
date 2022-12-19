package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 支付宝支付订单信息表
 *
 * @TableName payment
 */
@TableName(value = "payment")
@Data
public class Payment implements Serializable {
    /**
     * 支付ID
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户等级 user->普通用户 vip->vip用户 vip用户9折购买
     */
    private String userType;

    /**
     * 购买的商品ID
     */
    private Long productId;

    /**
     * 购买的商品数量
     */
    private Integer productNum;

    /**
     * 支付金额
     */
    private Long paymentAmount;

    /**
     * 优惠券ID
     */
    private Long voucherId;

    /**
     * 优惠金额
     */
    private Long preferentialAmount;

    /**
     * 最终支付金额
     */
    private Long finalPaymentAmount;

    /**
     * 支付状态 待支付 放弃支付 支付完成
     */
    private String paymentStatus;
    /*
     * 支付宝付款账号
     * */
    private String buyerId;
    /**
     * 支付宝回调内容
     */
    private String callbackContent;

    /**
     * 支付宝回调时间
     */
    private LocalDateTime callbackTime;

    /**
     *
     */
    private LocalDateTime createtime;

    /**
     *
     */
    private LocalDateTime updatatime;

    /**
     *
     */
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}