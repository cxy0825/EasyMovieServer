package com.cxy.entry;

import lombok.Data;

@Data
public class buyParam {
    //商品ID
    private String id;
    //商品数量
    private String productNum;
    //优惠券ID
    private String voucherID = "0";

}
