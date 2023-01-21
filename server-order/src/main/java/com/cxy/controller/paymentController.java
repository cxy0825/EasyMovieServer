package com.cxy.controller;

import com.cxy.entry.Payment;
import com.cxy.result.Result;
import com.cxy.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order/payment")
public class paymentController {
    @Resource
    PaymentService paymentService;

    //查询电影票信息
    @GetMapping("/Info/order/list")
    public Result getOrderList() {
        List<Payment> list = paymentService.getOrderList();
        return Result.ok().data(list);
    }
    
    //根据订单ID查询订单
    @GetMapping("/Info/{paymentID}")
    public Result getInfoByID(@PathVariable("paymentID") Long paymentID) {
        Payment payment = paymentService.getInfoByID(paymentID);
        return Result.ok().data(payment);
    }

    //根据订单ID更新订单
    @PostMapping("/update")
    public void update(@RequestBody Payment payment) {
        paymentService.updateById(payment);
    }


}
