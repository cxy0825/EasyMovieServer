package com.cxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.clients.mongo.MongoClient;
import com.cxy.entry.Film;
import com.cxy.result.Result;
import com.cxy.service.FilmInfoService;
import com.cxy.service.FilmService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


//影片相关的控制器
@RestController
@RequestMapping("/movie")
public class FilmController {
    @Resource
    FilmService filmService;
    @Resource
    FilmInfoService filmInfoService;

    @Resource
    MongoClient mongoClient;


    @GetMapping("/public/film/info/list/{cinemaID}/{page}/{limit}")
    public Result getFilmInfoList(
            @PathVariable Long cinemaID,
            @PathVariable Integer page,
            @PathVariable Integer limit) {
        Page<Film> filmPage = new Page<>(page, limit);
        LambdaQueryWrapper<Film> filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果电影院ID是0 就表示查询全部
        if (cinemaID != 0) {
            filmLambdaQueryWrapper.eq(Film::getCinemaId, cinemaID);
        }
        List<Film> list = filmService.list(filmLambdaQueryWrapper);
        return Result.ok().data(list);

    }

    /**
     * 通过id获取电影信息
     *
     * @return {@link Result}
     */
    @GetMapping("/public/film/info/{ID}")
    public Result getFilmInfoById(@PathVariable Long ID) {
        return filmService.getFilmInfoByID(ID);

    }

    @GetMapping("/public/film/info")
    public Result getFilmInfoByName(@RequestParam String name) {
        return filmService.getFilmInfoByName(name);

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

    /**
     * 删除电影
     *
     * @param ID id
     * @return {@link Result}
     */
    @DeleteMapping("/film/del/{ID}")
    public Result delFilm(@PathVariable("ID") Long ID) {
        boolean b = filmService.removeById(ID);
        //删除mongo里面的内容
        mongoClient.deleteFilmInfoById(ID);
        return Result.ok();

    }

}
