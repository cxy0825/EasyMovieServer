package com.cxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.entry.Moviehouse;
import com.cxy.result.Result;
import com.cxy.service.MoviehouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/movie")
public class MovieHouse {
    @Resource
    MoviehouseService moviehouseService;


    /**
     * 获取所有电影放映厅列表
     *
     * @return {@link Result}
     */
    @GetMapping("/movieHouse/list/{page}/{limit}")
    public Result getMovieHouseList(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {

        Page<Moviehouse> movieHousePage = new Page<>(page, limit);
        moviehouseService.page(movieHousePage, null);

        return Result.ok().data(movieHousePage);
    }
    
}
