package com.cxy.controller;

import com.cxy.entry.buyParam;
import com.cxy.entry.movieTicketParam;
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

    @GetMapping("/pay/{paymentID}")
    public Result aliPay(@PathVariable("paymentID") Long paymentID) throws Exception {
        return alipayService.alipay(paymentID);
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

    //购买商品(不限制数量)
    @PostMapping("/buy")
    public Result buyVoucher(@RequestBody movieTicketParam movieTicketParam) {
        return alipayService.buy(movieTicketParam);
    }
}
