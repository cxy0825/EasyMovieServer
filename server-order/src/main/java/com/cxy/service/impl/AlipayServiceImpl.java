package com.cxy.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.cxy.entry.OrderParam;
import com.cxy.result.Result;
import com.cxy.service.AlipayService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlipayServiceImpl implements AlipayService {
    @Resource
    RabbitTemplate rabbitTemplate;

    @Override
    public Result alipay(OrderParam orderParam) throws Exception {
        AlipayTradePagePayResponse payResponse = Factory.Payment.Page().optional("goods_type", orderParam.getType()).pay(
                orderParam.getSubject(),
                orderParam.getOutTradeNo(),
                orderParam.getTotalAmount(),
                ""
        );
        return Result.ok().message("订单创建成功").data(payResponse.getBody());
    }

    @Override
    public void payNotify(HttpServletRequest request) throws Exception {
        if (!request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            return;
        }
        System.out.println("=========支付宝异步回调========");
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
            System.out.println(name + " = " + request.getParameter(name));
        }
        //验签
        Boolean verifyNotify = Factory.Payment.Common().verifyNotify(params);
        //验签不通过
        if (!verifyNotify) {
            return;
        }
        //发送到消息队列
        //rabbitTemplate.convertAndSend("pay.voucher.order", "pay.voucher", "asd");
    }
}
