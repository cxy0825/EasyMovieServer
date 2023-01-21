package com.cxy.controller;

import com.cxy.entry.vo.param.LoginVo;
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

    //普通用户的登录
    @PostMapping("/login")
    public Result userLogin(@RequestBody LoginVo loginVo) {
        return userService.userLogin(loginVo);
    }

    /*
     *
     * 发送验证码到手机
     * */
    @GetMapping("public/code")
    public Result getCode(@RequestParam("phone") String phone) {
        return userService.code(phone);
    }

    //管理员的登录
    @PostMapping("/admin/login")
    public Result adminLogin(@RequestBody LoginVo loginVo) {
        return userService.adminLogin(loginVo);
    }

    @GetMapping("/getInfo")
    public Result adminInfo(@RequestHeader("token") String token) {
        return userService.getAdminInfo(token);
    }

    @PostMapping("/vip/open")
    public Result opening(@RequestHeader("token") String token) {
        return userService.openingVIP(token);

    }

}

