package com.cxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.Dto.MovieSetDto;
import com.cxy.entry.MovieSet;
import com.cxy.result.Result;
import com.cxy.service.MovieSetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieSetController {
    @Resource
    MovieSetService movieSetService;

    /**
     * 通过id获取排片信息
     *
     * @param ID id
     * @return {@link Result}
     */
    @GetMapping("public/movieSet/info/{ID}")
    public Result getMovieSetInfoById(@PathVariable Long ID) {
        MovieSetDto movieSetDto = movieSetService.getMovieSetInfoById(ID);
        return Result.ok().data(movieSetDto);
    }

    /**
     * 按照电影院ID 获得电影片场信息
     *
     * @param page     页面
     * @param limit    限制
     * @param cinemaId 电影院ID
     * @return {@link Result}
     */
    @GetMapping("public/movieSet/info/{page}/{limit}")
    public Result getMovieSetInfo(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @RequestParam(required = false) Long cinemaId
    ) {

        Page<MovieSetDto> movieSetPage = new Page<>(page, limit);

        return movieSetService.MovieSetInfo(movieSetPage, cinemaId);
    }

    /**
     * 通过电影名称获得电影片场信息
     *
     * @param name     电影名字
     * @param page     页面
     * @param limit    限制
     * @param cinemaID 电影院ID 可以为空
     * @return {@link Result}
     */
    @GetMapping("public/movieSet/info/{name}/{page}/{limit}")
    public Result getMovieSetInfoByFilmName(
            @PathVariable String name,
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @RequestParam(value = "cinemaID", required = false) Long cinemaID
    ) {
        Page<MovieSetDto> movieSetPage = new Page<>(page, limit);
        movieSetService.getMovieSetInfoByFilmName(movieSetPage, name, cinemaID);
        return Result.ok().data(movieSetPage);
    }

    /*
     * 根据电影院ID和电影ID查询今天的排片信息
     * */
    @GetMapping("public/movieSet/todayinfo/{cinemaID}/{filmID}")
    public Result getTodayInfo(@PathVariable("cinemaID") Long cinemaID, @PathVariable("filmID") Long filmID) {
        List<MovieSet> list = movieSetService.getTodayInfo(cinemaID, filmID);
        return Result.ok().data(list);
    }

    /**
     * 通过id删除电影排片信息
     *
     * @param ID id
     * @return {@link Result}
     */
    @DeleteMapping("/movieSet/delete/{ID}")
    public Result delMovieSetInfoById(@PathVariable Long ID) {
        boolean b = movieSetService.removeById(ID);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 更新电影排片信息
     *
     * @param movieSet 电影对象
     * @return {@link Result}
     */
    @PostMapping("/movieSet/update")
    public Result updateMovieSetInfo(@RequestBody MovieSet movieSet) {
        return movieSetService.updateMovieSetInfo(movieSet);
    }

    /**
     * 添加电影排片信息
     *
     * @param movieSet 电影
     * @return {@link Result}
     */
    @PostMapping("/movieSet/add")
    public Result addMovieSetInfo(@RequestBody MovieSet movieSet) {
        System.out.println(movieSet.toString());
        return movieSetService.addMovieSetInfo(movieSet);
    }

    //更加电影放映厅ID获得最后一场的信息
    @GetMapping("public/movieSet/lastInfo/{movieHouseID}")
    public Result getLastMovieInfo(@PathVariable Long movieHouseID) {

        return movieSetService.getLastMovieInfo(movieHouseID);
    }
}
