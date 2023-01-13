package com.cxy.service.impl;

import cn.hutool.json.JSONUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cxy.Utils.ThreadLocalUtil;
import com.cxy.clients.mongo.MongoClient;
import com.cxy.clients.mongo.RedisClient;
import com.cxy.entry.*;
import com.cxy.result.Result;
import com.cxy.result.ResultEnum;
import com.cxy.result.redisKey;
import com.cxy.service.AlipayService;
import com.cxy.service.HoldVoucherService;
import com.cxy.service.PaymentService;
import com.cxy.service.VoucherService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlipayServiceImpl implements AlipayService {
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    VoucherService voucherService;
    @Resource
    HoldVoucherService holdVoucherService;
    @Resource
    RedisClient redisClient;

    @Resource
    MongoClient mongoClient;
    @Resource
    PaymentService paymentService;

    @Override
    public Result alipay(Long paymentID) throws Exception {
//        System.out.println(orderParam.toString());
        //根据订单ID查询数据库
        Payment payment = paymentService.getById(paymentID);
        AlipayTradePagePayResponse payResponse =
                Factory.Payment.Page()

                        .pay(
                                String.valueOf(payment.getId()),
                                String.valueOf(payment.getId()),
                                String.valueOf(payment.getFinalPaymentAmount()),
                                ""
                        );
        return Result.ok().message("支付宝订单创建成功").data(payResponse.getBody());
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
        if (null == voucherInfo) {
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
        payment.setPaymentAmount(BigDecimal.valueOf(voucherInfo.getPrice()));//支付金额 价格是从数据库查出来的
        payment.setFinalPaymentAmount(BigDecimal.valueOf(voucherInfo.getPrice()));//最终支付金额,购买优惠券不能使用优惠券所以最终金额是一样的
        payment.setProductType("voucher");
        rabbitTemplate.convertAndSend("paymentExchange", "payment.create", JSONUtil.toJsonStr(payment));
        //加入延迟队列,开始计时30分钟 发送订单ID
        rabbitTemplate.convertAndSend("30M-delayQueue", assign_id.toString());
        return Result.ok().data(assign_id);
    }

    @Override
    public Result buy(movieTicketParam movieTicketParam) {
        //根据排片ID获取该片场票的价格
        MovieSet movieSet = mongoClient.getMovieSetByID(movieTicketParam.getMovieSetId());
        if (null == movieSet) {
            return Result.fail(ResultEnum.PROJECT_ERROR);
        }
        //获取用户ID
        Token token = (Token) ThreadLocalUtil.get();
        //电影票价格
        BigDecimal ticketPrice = movieSet.getPrice();

        //异步队列写入订单
//      因为队列是不能返回值的所以这里手动使用雪花算法生成ID
        Payment payment = new Payment();
        Long assign_id = IdWorker.getId();
        payment.setId(assign_id);
        payment.setUserId(token.getId());//购买用户的ID
        payment.setUserType(token.getPower());//用户的类型主要是不是VIP
        payment.setProductId(Long.valueOf(movieTicketParam.getMovieSetId()));//商品ID 这里结束优惠券的ID
        payment.setProductNum(movieTicketParam.getTickets().length);//商品数量
        payment.setProductType("ticket");
        BigDecimal number = BigDecimal.valueOf(movieTicketParam.getTickets().length);
        BigDecimal price = number.multiply(ticketPrice);//支付金额 价格是从数据库查出来的
        payment.setPaymentAmount(price);//支付金额
        //如果是VIP就进行九折
        if (token.getPower().equals("1")) {
            price.multiply(BigDecimal.valueOf(0.9));
        }

        //优惠券信息 判断是否符合优惠券的使用条件
        if (null != movieTicketParam.getVoucherId()) {

            Voucher voucher = voucherService.getById(movieTicketParam.getVoucherId());
            if (null != voucher) {
                //如果大于这张优惠券的满减金额就可以相减
                int flag = price.compareTo(BigDecimal.valueOf(voucher.getPayValue()));
                if (flag >= 0) {
                    payment.setPreferentialAmount(BigDecimal.valueOf(voucher.getActualValue()));//优惠金额
                    payment.setVoucherId(movieTicketParam.getVoucherId());//优惠券ID

                    holdVoucherService.update(
                            new LambdaUpdateWrapper<HoldVoucher>()
                                    .eq(HoldVoucher::getUserId, token.getId())
                                    .eq(HoldVoucher::getVoucherId, movieTicketParam.getVoucherId())
                                    .set(HoldVoucher::getState, 0)
                    );

                }
            }

        }

        payment.setFinalPaymentAmount(price.subtract(payment.getPreferentialAmount()));//最终支付金额
        //写入座位信息
        payment.setProductInfo(JSONUtil.toJsonStr(movieTicketParam.getTickets()));
        //发送到消息队列
        rabbitTemplate.convertAndSend("paymentExchange", "payment.create", JSONUtil.toJsonStr(payment));
        //在mongo中添加已经被购买的片场信息
        mongoClient.addSeat(movieTicketParam.getMovieSetId(), movieTicketParam.getTickets());

        //加入延迟队列,开始计时30分钟 发送订单ID
        rabbitTemplate.convertAndSend("30M-delayQueue", assign_id.toString());

        return Result.ok().data(assign_id);
    }
}
