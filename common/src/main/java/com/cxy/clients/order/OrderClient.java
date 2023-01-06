package com.cxy.clients.order;

import com.cxy.entry.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "server-order", contextId = "order", path = "/order/payment")
public interface OrderClient {
    //根据订单ID查询订单
    @GetMapping("/Info/{paymentID}")
    public Payment getInfoByID(@PathVariable("paymentID") Long paymentID);

    //根据订单ID更新订单
    @PostMapping("/update")
    public void update(@RequestBody Payment payment);
}
