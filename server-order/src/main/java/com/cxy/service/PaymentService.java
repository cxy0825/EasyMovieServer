package com.cxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.entry.Payment;
import org.springframework.amqp.core.Message;

import java.util.List;

/**
 * @author Cccxy
 * @description 针对表【payment(支付宝支付订单信息表)】的数据库操作Service
 * @createDate 2022-12-19 15:36:58
 */
public interface PaymentService extends IService<Payment> {
    //创建订单
    void createPayment(Message message);

    //支付订单
    void paymentCallback(Message message);

    Payment getInfoByID(Long paymentID);

    List<Payment> getOrderList();
}
