package com.cxy.service;

import com.cxy.common.r;

public interface GongsiServer {
    r getInfo(Long id);

    r getAll(Integer page, Integer limit);

    r getList();
}
