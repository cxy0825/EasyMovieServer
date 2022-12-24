package com.cxy.controller;

import com.cxy.entry.Token;
import com.cxy.result.redisKey;
import com.cxy.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("redis")
public class RedisUserController {
    @Resource
    UserService userService;
    @Resource
    RedisTemplate redisTemplate;

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


    /**
     * 把登录的验证码写入redis 5分钟有效期
     *
     * @param userID 用户id
     * @param code   代码
     * @return boolean
     */
    @GetMapping("/setCode")
    public boolean setCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {

        try {
            redisTemplate.opsForValue().set(redisKey.USER_CODE.getKey() + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 获取登录验证码
     *
     * @param userID 用户id
     * @return {@link String}
     */
    @GetMapping("/getCode")
    public String getCode(@RequestParam("phone") String phone) {
        try {
            return (String) redisTemplate.opsForValue().get(redisKey.USER_CODE.getKey() + phone);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
