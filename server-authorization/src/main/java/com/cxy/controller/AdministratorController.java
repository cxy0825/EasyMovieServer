package com.cxy.controller;

import com.cxy.entry.vo.param.AdministratorParam;
import com.cxy.result.Result;
import com.cxy.service.AdministratorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin")
public class AdministratorController {
    @Resource
    AdministratorService administratorService;

    @PostMapping("/login")
    public Result login(@RequestBody AdministratorParam administratorParam) {
        return administratorService.login(administratorParam);
    }
}
