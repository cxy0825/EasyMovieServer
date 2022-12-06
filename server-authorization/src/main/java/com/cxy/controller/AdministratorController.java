package com.cxy.controller;

import com.cxy.entry.vo.param.AdministratorParam;
import com.cxy.result.Result;
import com.cxy.service.AdministratorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin")
public class AdministratorController {
    @Resource
    AdministratorService administratorService;

    //管理员的登录
    @PostMapping("/login")
    public Result login(@RequestBody AdministratorParam administratorParam) {
        return administratorService.login(administratorParam);
    }

    //管理员验证token
    //管理员获得用户信息
    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token) {
        return administratorService.getInfo(token);
    }
}
