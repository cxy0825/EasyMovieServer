package com.cxy.service.impl;

import cn.hutool.json.JSONUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cxy.Utils.ThreadLocalUtil;
import com.cxy.clients.mongo.RedisClient;
import com.cxy.entry.*;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import com.cxy.result.redisKey;
import com.cxy.service.AlipayService;
import com.cxy.service.VoucherService;
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
    @Resource
    VoucherService voucherService;
    @Resource
    RedisClient redisClient;

    @Override
    public Result alipay(OrderParam orderParam) throws Exception {
//        System.out.println(orderParam.toString());
        AlipayTradePagePayResponse payResponse =
                Factory.Payment.Page()
                        .optional("goods_type", orderParam.getType())
                        .pay(
                                orderParam.getSubject(),
                                orderParam.getOutTradeNo(),
                                orderParam.getTotalAmount(),
                                ""
                        );
        return Result.ok().message("订单创建成功").data(payResponse.getBody());
    }

    @Override
    public Result payNotify(HttpServletRequest request) throws Exception {
        if (!request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("订单没有被成功支付");
            return Result.fail(ResultEnum.PAYMENT_ERROR);
        }
        System.out.println("=========支付宝异步回调========");
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
//            System.out.println(name + " = " + request.getParameter(name));
        }
        //验签
        Boolean verifyNotify = Factory.Payment.Common().verifyNotify(params);
        //验签不通过
        if (!verifyNotify) {
            return Result.fail(ResultEnum.PAYMENT_SIGN_FAIL);
        }
        //发送到消息队列
        rabbitTemplate.convertAndSend("paymentExchange", "payment.complete", JSONUtil.toJsonStr(params));
        return Result.ok().message("支付成功");
    }

    @Override
    public Result buyVoucherLimit(buyParam buyParam) {
        //拼接参数
        String stockKey = redisKey.VOUCHER_STOCK.getKey() + buyParam.getId().toString();
        String boughtKey = redisKey.VOUCHER_ALREADY_BOUGHT.getKey() + buyParam.getId().toString();
        //根据ID获取优惠券信息
        Voucher voucherInfo = voucherService.getById(buyParam.getId());
        if (voucherInfo == null) {
            return Result.fail(ResultEnum.PROJECT_ERROR);
        }
        //获取用户ID
        Token token = (Token) ThreadLocalUtil.get();

        //redis中购买成功后创建消息队列送到订单生成列表
        Boolean flag = redisClient.limitBought(stockKey, boughtKey, token.getId(), "1");
        //redis扣减库存和存入用户成功
        if (!flag) {
            return Result.fail().message("购买失败");
        }
        //异步队列写入订单
//        因为队列是不能返回值的所以这里手动使用雪花算法生成ID
        Payment payment = new Payment();
        Long assign_id = IdWorker.getId();
        payment.setId(assign_id);
        payment.setUserId(token.getId());//购买用户的ID
        payment.setUserType(token.getPower());//用户的类型主要是不是VIP
        payment.setProductId(Long.valueOf(buyParam.getId()));//商品ID 这里结束优惠券的ID
        payment.setPaymentAmount(voucherInfo.getPrice());//支付金额 价格是从数据库查出来的
        payment.setFinalPaymentAmount(voucherInfo.getPrice());//最终支付金额,购买优惠券不能使用优惠券所以最终金额是一样的

        rabbitTemplate.convertAndSend("paymentExchange", "payment.create", JSONUtil.toJsonStr(payment));
        return Result.ok().data(assign_id);
    }

    @Override
    public Result buyVoucher(buyParam buyParam) {
        //拼接参数
        String stockKey = redisKey.VOUCHER_STOCK.getKey() + buyParam.getId().toString();
        //根据ID获取优惠券信息
        Voucher voucherInfo = voucherService.getById(buyParam.getId());
        if (voucherInfo == null) {
            return Result.fail(ResultEnum.PROJECT_ERROR);
        }
        //获取用户ID
        Token token = (Token) ThreadLocalUtil.get();

        //redis中购买成功后创建消息队列送到订单生成列表
        Boolean flag = redisClient.bought(stockKey, token.getId(), buyParam.getProductNum());
        //redis扣减库存
        if (!flag) {
            return Result.fail().message("购买失败");
        }
        //异步队列写入订单
//        因为队列是不能返回值的所以这里手动使用雪花算法生成ID
        Payment payment = new Payment();
        Long assign_id = IdWorker.getId();
        payment.setId(assign_id);
        payment.setUserId(token.getId());//购买用户的ID
        payment.setUserType(token.getPower());//用户的类型主要是不是VIP
        payment.setProductId(Long.valueOf(buyParam.getId()));//商品ID 这里结束优惠券的ID
        payment.setProductNum(Integer.valueOf(buyParam.getProductNum()));//商品数量
        Long price = voucherInfo.getPrice() * Long.valueOf(buyParam.getProductNum());//支付金额 价格是从数据库查出来的
        payment.setPaymentAmount(price);
        payment.setFinalPaymentAmount(price);//最终支付金额,购买优惠券不能使用优惠券所以最终金额是一样的

        rabbitTemplate.convertAndSend("paymentExchange", "payment.create", JSONUtil.toJsonStr(payment));
        return Result.ok().data(assign_id);
    }
}
