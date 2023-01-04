package com.cxy.controller;

import com.cxy.entry.Cinema;
import com.cxy.result.Result;
import com.cxy.service.CinemaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/movie/cinema")
public class CinemaController {
    @Resource
    CinemaService cinemaService;

    //添加或者更新电影院
    @PostMapping("/add")
    public Result add(@RequestBody Cinema cinema) {
        boolean b = cinemaService.add(cinema);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }

    }

    @GetMapping("public/info/{ID}")
    public Result getInfo(@PathVariable("ID") Long ID) {
        Cinema cinema = cinemaService.getInfo(ID);
        return Result.ok().data(cinema);
    }
}
