package com.cxy.clients.mongo;

import com.cxy.entry.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "server-mongo", path = "/redis", contextId = "redisServer")
public interface RedisClient {
    @GetMapping("/getInfo")
    public Token getUserInfo(@RequestParam("account") String account, @RequestParam("key") String key);

    @PostMapping("/setInfo")
    public boolean setUserInfo(@RequestParam("account") String account, @RequestParam("key") String key, @RequestBody Map<String, Object> userMap);

    @GetMapping("/stock/getStock/{key}")
    public Long getStock(@PathVariable("key") String key);

    @PostMapping("/stock/setStock/{key}/{stockNum}")
    public boolean setStock(@PathVariable("key") String key, @PathVariable("stockNum") Long stockNum);

    @GetMapping("/stock/alreadyBought/{key}")
    public List<Long> getAlreadyBought(@PathVariable("key") String key);

    @PostMapping("/stock/limitBought/{stockKey}/{boughtKey}/{userId}")
    public boolean limitBought(
            @PathVariable("stockKey") String stockKey,
            @PathVariable("boughtKey") String boughtKey,
            @PathVariable("userId") Long userId
    );

    @PostMapping("/stock/bought/{stockKey}")
    public boolean bought(@PathVariable("stockKey") String stockKey);
}
