package com.cxy.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.VoucherOrder;
import com.cxy.mapper.VoucherOrderMapper;
import com.cxy.service.VoucherOrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author Cccxy
 * @description 针对表【voucher_order】的数据库操作Service实现
 * @createDate 2022-12-18 13:51:47
 */
@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder>
        implements VoucherOrderService {
    @Override
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("voucherQueue"),
                    exchange = @Exchange(value = "order", type = "direct"),
                    key = {"order.voucher"}
            )
    })
    public void createVoucherOrder(Message message) {
        try {
            String msg = new String(message.getBody(), "utf-8");
            VoucherOrder voucherOrder = JSONUtil.toBean(msg, VoucherOrder.class);
            //写入到voucherOrder表
            baseMapper.insert(voucherOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




