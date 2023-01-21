package com.cxy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.Utils.ThreadLocalUtil;
import com.cxy.entry.HoldVoucher;
import com.cxy.entry.Payment;
import com.cxy.entry.Token;
import com.cxy.mapper.PaymentMapper;
import com.cxy.service.HoldVoucherService;
import com.cxy.service.PaymentService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Cccxy
 * @description 针对表【payment(支付宝支付订单信息表)】的数据库操作Service实现
 * @createDate 2022-12-19 15:36:58
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>
        implements PaymentService {


    @Resource
    HoldVoucherService holdVoucherService;

    @Resource
    PaymentService paymentService;

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
        System.out.println("订单存入数据库完成");
    }

    //支付成功后的回调
    //把payment里面的状态变成已支付和写入数据
    //在hold_voucher表中写入优惠券ID和用户ID
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
        //根据订单ID去查询订单
        Payment payment = baseMapper.selectById(jsonObj.getStr("out_trade_no"));
        Long productId = payment.getProductId();
        Long userId = payment.getUserId();
        if (payment.getProductType().equals("voucher")) {
            //在优惠券持有表把优惠券 和用户ID写入
            HoldVoucher holdVoucher = new HoldVoucher();
            holdVoucher.setVoucherId(productId);
            holdVoucher.setUserId(userId);
            holdVoucherService.save(holdVoucher);
            System.out.println("优惠券持有表添加数据完成");
        }


    }

    @Override
    public Payment getInfoByID(Long paymentID) {

        LambdaQueryWrapper<Payment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Payment::getId, paymentID);
        Payment payment = baseMapper.selectOne(queryWrapper);
        return payment;
    }

    @Override
    public List<Payment> getOrderList() {

        Token token = (Token) ThreadLocalUtil.get();
        System.out.println(token);
        Long userId = token.getId();
        LambdaQueryWrapper<Payment> paymentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        paymentLambdaQueryWrapper.eq(Payment::getProductType, "ticket");
        paymentLambdaQueryWrapper.eq(Payment::getUserId, userId);
        paymentLambdaQueryWrapper.select(Payment.class, item -> !item.getColumn().equals("callback_content"));
//        paymentLambdaQueryWrapper.(Payment::getUserId, userId);
        List<Payment> payments = baseMapper.selectList(paymentLambdaQueryWrapper);
        return payments;
    }

}




