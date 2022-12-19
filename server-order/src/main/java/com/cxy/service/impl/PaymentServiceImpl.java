package com.cxy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.entry.Payment;
import com.cxy.mapper.PaymentMapper;
import com.cxy.service.PaymentService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author Cccxy
 * @description 针对表【payment(支付宝支付订单信息表)】的数据库操作Service实现
 * @createDate 2022-12-19 15:36:58
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>
        implements PaymentService {


    //创建订单
    @Override
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "paymentQueue"),
            exchange = @Exchange(name = "paymentExchange", type = ExchangeTypes.DIRECT),
            key = {"payment.create"}
    ))
    public void createPayment(Message message) {
        String msg = new String(message.getBody());
        //json转成对象
        Payment payment = JSONUtil.toBean(msg, Payment.class);
        //存入到数据库
        baseMapper.insert(payment);
        System.out.println("订单出入数据库完成");
    }

    @Override
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "paymentCallbackQueue"),
            exchange = @Exchange(name = "paymentExchange", type = ExchangeTypes.DIRECT),
            key = {"payment.complete"}
    ))
    public void paymentCallback(Message message) {
        String msg = new String(message.getBody());
        JSONObject jsonObj = JSONUtil.parseObj(msg);
        LambdaUpdateWrapper<Payment> paymentLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        paymentLambdaUpdateWrapper.eq(Payment::getId, jsonObj.getStr("out_trade_no"));
        paymentLambdaUpdateWrapper.set(Payment::getPaymentStatus, "已支付");
        paymentLambdaUpdateWrapper.set(Payment::getCallbackContent, msg);
        paymentLambdaUpdateWrapper.set(Payment::getCallbackTime, jsonObj.get("notify_time"));
        paymentLambdaUpdateWrapper.set(Payment::getBuyerId, jsonObj.getStr("buyer_id"));
        //更新到数据库
        baseMapper.update(null, paymentLambdaUpdateWrapper);
        System.out.println("支付宝回调写入数据库完成");
       

    }

}




