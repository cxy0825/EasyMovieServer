package com.cxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.Dto.MovieSetDto;
import com.cxy.entry.MovieSet;
import com.cxy.result.Result;
import com.cxy.service.MovieSetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
     * 获得电影片场信息
     *
     * @param page  页面
     * @param limit 限制
     * @return {@link Result}
     */
    @GetMapping("public/movieSet/info/{page}/{limit}")
    public Result getMovieSetInfo(@PathVariable Integer page, @PathVariable Integer limit) {
        Page<MovieSetDto> movieSetPage = new Page<>(page, limit);
        movieSetService.getMovieSetInfo(movieSetPage);

        return Result.ok().data(movieSetPage);
    }

    /**
     * 通过电影名称获得电影片场信息
     *
     * @param page  页面
     * @param limit 限制
     * @return {@link Result}
     */
    @GetMapping("public/movieSet/info/{name}/{page}/{limit}")
    public Result getMovieSetInfoByFilmName(@PathVariable String name, @PathVariable Integer page, @PathVariable Integer limit) {
        Page<MovieSetDto> movieSetPage = new Page<>(page, limit);
        movieSetService.getMovieSetInfoByFilmName(movieSetPage, name);
        return Result.ok().data(movieSetPage);
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

    @PostMapping("/movieSet/update")
    public Result updateMovieSetInfo(@RequestBody MovieSet movieSet) {
        return movieSetService.updateMovieSetInfo(movieSet);
    }
}
