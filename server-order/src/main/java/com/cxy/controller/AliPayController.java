package com.cxy.controller;

import com.cxy.entry.OrderParam;
import com.cxy.entry.buyParam;
import com.cxy.result.Result;
import com.cxy.service.AlipayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order/aliPay")
public class AliPayController {
    @Resource
    AlipayService alipayService;

    @GetMapping("/pay")
    public Result aliPay(OrderParam orderParam) throws Exception {
        return alipayService.alipay(orderParam);
    }

    @PostMapping("/payNotify")
    public void payNotify(HttpServletRequest request) throws Exception {

        alipayService.payNotify(request);
    }


    //购买优惠券商品(限制购买1张)
    @PostMapping("/limit/buyVoucher")
    public Result buyVoucherLimit(@RequestBody buyParam buyParam) {
        return alipayService.buyVoucherLimit(buyParam);
    }

    //购买优惠券商品(不限制数量)
    @PostMapping("/buyVoucher")
    public Result buyVoucher(@RequestBody buyParam buyParam) {
        return alipayService.buyVoucher(buyParam);
    }
}
