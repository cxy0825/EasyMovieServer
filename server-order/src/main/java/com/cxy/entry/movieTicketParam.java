package com.cxy.entry;

import lombok.Data;

@Data
public class movieTicketParam {
    //排片ID
    private Long movieSetId;
    //购买的座位列表
    private Integer[][] tickets;
    //优惠券ID
    private Long voucherId;
}
