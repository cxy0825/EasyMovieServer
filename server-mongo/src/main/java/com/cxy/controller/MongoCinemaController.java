package com.cxy.controller;

import com.cxy.entry.mongoEntry.MongoCinema;
import com.cxy.result.Result;
import com.cxy.service.MongoCinemaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/mongo/cinema")
public class MongoCinemaController {
    @Resource
    MongoCinemaService mongoCinemaService;

    //把数据添加到mongo
    @PostMapping("/add")
    public boolean add(@RequestBody MongoCinema mongoCinema) {
        boolean b = mongoCinemaService.add(mongoCinema);
        return b;
    }

    //查询当前范围内的电影院
    @GetMapping("/info")
    public Result info(
            @RequestParam("x") double x,
            @RequestParam("y") double y,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        List<MongoCinema> list = mongoCinemaService.info(x, y, page, limit);
        return Result.ok().data(list);
    }

    /**
     * 删除指定id
     *
     * @param ID id
     * @return {@link Result}
     */
    @GetMapping("/del")
    public boolean del(@RequestParam("id") Long ID) {
        boolean b = mongoCinemaService.del(ID);
        return b;
    }
}
