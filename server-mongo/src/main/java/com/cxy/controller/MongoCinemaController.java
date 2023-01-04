package com.cxy.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cxy.entry.Cinema;
import com.cxy.entry.mongoEntry.MongoCinema;
import com.cxy.result.Result;
import com.cxy.service.MongoCinemaService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/mongo/cinema")
public class MongoCinemaController {
    @Resource
    MongoCinemaService mongoCinemaService;
    @Resource
    MongoTemplate mongoTemplate;

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
            @RequestParam("limit") int limit,
            @RequestParam("sort") String sort

    ) {
        List<MongoCinema> list = mongoCinemaService.info(x, y, page, limit, sort);
        return Result.ok().data(list);
    }

    //根据指定的电影名称查询附近的电影院
    @GetMapping("/info/name")
    public Result infoByFilmID(
            @RequestParam("x") double x,
            @RequestParam("y") double y,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam("sort") String sort,
            @RequestParam("filmName") String filmName

    ) {
        List<MongoCinema> list = mongoCinemaService.infoByFilmID(x, y, page, limit, sort, filmName);
        return Result.ok().data(list);
    }

    @GetMapping("/info/{ID}")
    public Result getInfo(@PathVariable("ID") Long ID) {
        MongoCinema mongoCinema = mongoTemplate.findById(ID, MongoCinema.class);
        Cinema cinema = new Cinema();
        BeanUtil.copyProperties(mongoCinema, cinema);
        return Result.ok().data(cinema);
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
