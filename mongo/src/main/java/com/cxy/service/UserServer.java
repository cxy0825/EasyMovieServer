package com.cxy.service;

import com.cxy.common.r;
import com.cxy.entry.User;

public interface UserServer {

    r login(User user);

    r getChiyou();

    r getShouchang();

    r buy(Long ID, Long number, double price);

    r sell(Long id);

    r add(Long id);

    r q(Long id);
}
