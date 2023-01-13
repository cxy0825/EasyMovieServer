package com.cxy.service.impl;

import cn.hutool.json.JSONUtil;
import com.cxy.Utils.DingDingUtil;
import com.cxy.clients.order.OrderClient;
import com.cxy.entry.Payment;
import com.cxy.result.Result;
import com.cxy.service.DeadService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Service
public class DeadServiceImpl implements DeadService {
    final String CANCEL_STATUS = "超时取消";
    @Resource
    OrderClient orderClient;

    //取消订单
    //em_order数据库中的payment表的payment_status变成超时取消
    @Override
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dead.order"),
            exchange = @Exchange(name = "deadExchange", type = ExchangeTypes.DIRECT),
            key = {"order.dead"}
    ))
    public void cancelOrder(Message message) {
        try {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println(msg.toString());
            Long paymentID = Long.valueOf(msg);
            //根据订单号查询订单数据库
            Result paymentInfo = orderClient.getInfoByID(paymentID);
            Payment payment = JSONUtil.toBean((String) paymentInfo.getData(), Payment.class);
            //如果payment_status是待支付就修改成超时取消
            if (null != payment && payment.getPaymentStatus().equals("待支付")) {
                //设置为超时取消
                payment.setPaymentStatus(CANCEL_STATUS);
                //修改状态
                orderClient.update(payment);
                //去mongo中取消座位信息
                //发送短信
                new DingDingUtil().sendMsg("用户ID:" + payment.getUserId() + "\n" + "购买的商品:" + payment.getProductId() + "\n 超过30分钟未付款,订单已经自动取消");

            }
        } catch (Exception e) {
            System.out.println("死信队列出错");
            e.printStackTrace();
        }


    }
}
