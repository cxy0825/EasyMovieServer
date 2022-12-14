package com.cxy.controller;

import com.cxy.entry.Token;
import com.cxy.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("redis")
public class RedisUserController {
    @Resource
    UserService userService;

    @GetMapping("/getInfo")
    public Token getUserInfo(String account, String key) {

        return userService.getUserInfo(account, key);
    }

    /**
     * 设置用户信息
     *
     * @param account 账号
     * @param key     JWT字符串的第三部分
     * @param userMap 需要保存的用户数据
     * @return boolean
     */
    @PostMapping("/setInfo")
    public boolean setUserInfo(String account, String key, @RequestBody Map<String, Object> userMap) {
        return userService.setUserInfo(account, key, userMap);
    }
}
