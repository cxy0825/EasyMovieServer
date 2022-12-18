package com.cxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.VoucherOrder;
import org.springframework.amqp.core.Message;

/**
 * @author Cccxy
 * @description 针对表【voucher_order】的数据库操作Service
 * @createDate 2022-12-18 13:51:47
 */
public interface VoucherOrderService extends IService<VoucherOrder> {
    public void createVoucherOrder(Message message);
}
