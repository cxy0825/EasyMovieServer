package com.cxy.clients;

import com.cxy.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "server-authorization")
public interface AuthorizationClient {
    @PostMapping("/user/verify")
    public Result verify(@RequestHeader("token") String token,
                         @RequestHeader("refreshToken") String refreshToken);

    @GetMapping("/user/t")
    public Result t();

    @PostMapping("/user/aa")
    public void aa();
}
