package com.cxy.controller;

import com.cxy.clients.mongo.MongoClient;
import com.cxy.entry.Film;
import com.cxy.mapper.FilmMapper;
import com.cxy.result.Result;
import com.cxy.service.FilmInfoService;
import com.cxy.service.FilmService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


//影片相关的控制器
@RestController
@RequestMapping("/movie")
public class FilmController {
    @Resource
    FilmService filmService;
    @Resource
    FilmInfoService filmInfoService;
    @Resource
    FilmMapper filmMapper;
    @Resource
    MongoClient mongoClient;

    /**
     * 通过id获取电影信息
     *
     * @return {@link Result}
     */
    @GetMapping("/public/film/info/{ID}")
    public Result getFilmInfoById(@PathVariable Long ID) {
        return filmService.getFilmInfoByID(ID);

    }

    /*
     *
     * 更新电影的基本信息
     * */
    @PostMapping("/film/update")
    public Result saveOrUpdate(@RequestBody Film film) {
        boolean update = filmService.saveOrUpdate(film);
        //更新就删除mongo中的缓存
        Long id = film.getId();
        if (id != null) {
            //删除mongo里面的内容
            mongoClient.deleteFilmInfoById(id);
        }
        if (update) {
            return Result.ok();
        } else {
            return Result.fail();
        }

    }


}
