package com.cxy.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 用户持有优惠券信息
 * @TableName hold_voucher
 */
@TableName(value ="hold_voucher")
@Data
public class HoldVoucher implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 优惠券ID
     */
    private Long voucherId;

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