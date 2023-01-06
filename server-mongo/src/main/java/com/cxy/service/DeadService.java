package com.cxy.service;

import org.springframework.amqp.core.Message;

public interface DeadService {
    //取消订单队列
    public void cancelOrder(Message message);
}
