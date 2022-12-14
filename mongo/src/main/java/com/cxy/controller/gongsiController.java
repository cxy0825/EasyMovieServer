package com.cxy.controller;

import com.cxy.common.r;
import com.cxy.service.GongsiServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mongo/gongsi")
@CrossOrigin
public class gongsiController {
    @Resource
    GongsiServer gongsiServer;

    @GetMapping("/info/{ID}")
    public r getGongsi(@PathVariable Long ID) {
        return gongsiServer.getInfo(ID);
    }

    @GetMapping("/info/all")
    public r getAll() {
        return gongsiServer.getList();
    }

    //全部基金列表
    @GetMapping("all/{page}/{limit}")
    public r getList(@PathVariable Integer page, @PathVariable Integer limit) {
        return gongsiServer.getAll(page, limit);
    }

}
