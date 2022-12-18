package com.cxy.entry;

import lombok.Data;

@Data
public class OrderParam {
    String subject;
    String outTradeNo;
    String totalAmount;
    String type;
}
