package com.cxy.service;

import com.cxy.entry.OrderParam;
import com.cxy.entry.buyParam;
import com.cxy.entry.movieTicketParam;
import com.cxy.result.Result;

import javax.servlet.http.HttpServletRequest;

public interface AlipayService {
    Result alipay(OrderParam orderParam) throws Exception;

    Result payNotify(HttpServletRequest request) throws Exception;

    Result buyVoucherLimit(buyParam buyParam);

    Result buy(movieTicketParam movieTicketParam);
}
