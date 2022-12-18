package com.cxy.service;

import com.cxy.entry.OrderParam;
import com.cxy.result.Result;

import javax.servlet.http.HttpServletRequest;

public interface AlipayService {
    Result alipay(OrderParam orderParam) throws Exception;

    void payNotify(HttpServletRequest request) throws Exception;
}
