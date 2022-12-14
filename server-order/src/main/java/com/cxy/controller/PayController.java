package com.cxy.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.cxy.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class PayController {
    @GetMapping("/pay")
    public Result pay() throws Exception {

        AlipayTradePagePayResponse payResponse = Factory.Payment.Page().pay(
                "中文",
                String.valueOf(Instant.now().getEpochSecond()),
                "100",
                ""
        );
        return Result.ok().message("测试支付通过").data(payResponse.getBody());
    }

    //支付成功后的回调
    @PostMapping("/payNotify")
    public Result payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            System.out.println(requestParams.toString());
            System.out.println("=================================");
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                System.out.println(name + " = " + request.getParameter(name));
            }
            //验签
            System.out.println(Factory.Payment.Common().verifyNotify(params));
        }

        return Result.ok().message("支付完成");
    }
}
