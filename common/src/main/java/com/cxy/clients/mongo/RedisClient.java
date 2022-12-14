package com.cxy.clients.mongo;

import com.cxy.entry.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "server-mongo", path = "/redis", contextId = "redisServer")
public interface RedisClient {
    @GetMapping("/getInfo")
    public Token getUserInfo(@RequestParam("account") String account, @RequestParam("key") String key);

    @PostMapping("/setInfo")
    public boolean setUserInfo(@RequestParam("account") String account, @RequestParam("key") String key, @RequestBody Map<String, Object> userMap);
}
