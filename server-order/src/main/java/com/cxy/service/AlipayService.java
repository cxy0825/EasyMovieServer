package com.cxy.service;

import com.cxy.entry.buyParam;
import com.cxy.entry.movieTicketParam;
import com.cxy.result.Result;

import javax.servlet.http.HttpServletRequest;

public interface AlipayService {
    Result alipay(Long paymentID) throws Exception;

    Result payNotify(HttpServletRequest request) throws Exception;

    Result buyVoucherLimit(buyParam buyParam);

    Result buy(movieTicketParam movieTicketParam);
}
