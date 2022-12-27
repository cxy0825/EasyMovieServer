package com.cxy.controller;

import com.cxy.result.Result;
import com.cxy.service.PerformerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/movie/performer")
public class PerformerController {
    @Resource
    PerformerService performerService;

    //查询演员信息
    @GetMapping("public/info/{ID}")
    public Result getInfoByFilmID(@PathVariable("ID") Long ID) {

        return performerService.getInfo(ID);
    }
}
