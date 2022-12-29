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

    //根据电影ID查询演员信息
    @GetMapping("public/info/{filmID}")
    public Result getInfoByFilmID(@PathVariable("filmID") Long ID) {
        return performerService.getInfo(ID);
    }

    //根据演员ID查询演员的详细信息
    @GetMapping("public/performerInfo/{ID}")
    public Result getInfoByID(@PathVariable("ID") Long ID) {
        return performerService.getInfoByID(ID);
    }

    //根据演员ID查询演员演过什么电影
    @GetMapping("public/performInfo/{ID}")
    public Result getPerformByID(@PathVariable("ID") Long ID) {
        return performerService.getPerformByID(ID);
    }
}
