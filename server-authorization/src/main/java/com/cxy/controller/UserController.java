package com.cxy.controller;

import com.cxy.entry.vo.param.LoginParam;
import com.cxy.result.Result;
import com.cxy.service.AuthorizationService;
import com.cxy.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户基本信息表(User)表控制层
 *
 * @author makejava
 * @since 2022-11-28 13:29:58
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;
    @Resource
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginParam loginParam) {
        return userService.login(loginParam);
    }

    //token认证
    @PostMapping("/verify")
    public Result verify(
            @RequestHeader("token") String token,
            @RequestHeader("refreshToken") String refreshToken) {

        return authorizationService.CheckToken(token, refreshToken);
    }


}

