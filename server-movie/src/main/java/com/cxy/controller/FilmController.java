package com.cxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cxy.entry.Film;
import com.cxy.entry.FilmInfo;
import com.cxy.result.Result;
import com.cxy.service.FilmInfoService;
import com.cxy.service.FilmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//影片相关的控制器
@RestController
@RequestMapping("/movie")
public class FilmController {
    @Resource
    FilmService filmService;
    @Resource
    FilmInfoService filmInfoService;
    
    /**
     * 通过id获取电影信息
     *
     * @return {@link Result}
     */
    @GetMapping("/public/film/info/{ID}")
    public Result getFilmInfoById(@PathVariable Long ID) {
        Film film = filmService.getById(ID);
        LambdaQueryWrapper<FilmInfo> filmInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filmInfoLambdaQueryWrapper.eq(FilmInfo::getFilmId, ID);
        List<FilmInfo> list = filmInfoService.list(filmInfoLambdaQueryWrapper);
        Set<String> videoUrls = new HashSet<>();
        Set<String> posterUrls = new HashSet<>();
        //获得电影宣传海报
        list.stream().forEach(item -> {
            videoUrls.add(item.getVideoUrl());
            posterUrls.add(item.getPosterUrl());
        });
        film.setVideoUrls(videoUrls);
        film.setPosterUrls(posterUrls);
        //获得电影标签

        //存到mongo中
        return Result.ok().data(film);
    }
}
